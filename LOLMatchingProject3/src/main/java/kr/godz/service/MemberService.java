package kr.godz.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;

import kr.godz.dao.MemberDAO;
import kr.godz.vo.MemberVO;

@Service
public class MemberService {

	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	
	// Join의 결과 ; DB에 저장
	public void insert(MemberVO vo) {
		logger.info("insert call : " + vo);

		// 이메일 인증 키 생성 ; size 만큼의 key생성
		String emailToken = generateToken(18);
		vo.setEmailToken(emailToken);
		
		// PW를 Crypt Encoder로 암호화해서 DB에 저장
		String encPassword = bCryptPasswordEncoder.encode(vo.getPassword());
		vo.setPassword(encPassword);

		// lol_member table에 사용자 정보 저장
		memberDAO.insert(vo);
		
		// lol_member_role table에 권한 정보 저장 : 기본값은 USER
		memberDAO.insertRole(vo.getUserId());
		
		sendEmail(vo);
	}
	
	public MemberVO selectByUserId(String userId) {
		MemberVO vo = null;
		vo = memberDAO.selectByUserId(userId);
		return vo;
	}
	
	// emailToken 생성하는 메소드
	public String generateToken(int keySize) {
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int k = 0;
		while(sb.length() < keySize) {
			k = r.nextInt(75) + 48;
			if((k >= 48 && k <= 57) || (k >= 65 && k <= 90) || (k >= 97 && k <= 122)) {
				sb.append((char)k);
			}
		}
		return sb.toString();
	}

	// Login ; 입력한 정보(userId, password)에서 userId에 해당하는 DB정보를 불러와서 password를 비교
	public MemberVO loginValidation(String userId, String password) {
		logger.info("loginValidation call : " + userId + ", " + password);
		
		// userId를 통해 DB에 저장된 정보를 얻어옴
		MemberVO dbvo = memberDAO.selectByUserId(userId);
		
		// userId에 해당하는 userId가 DB에 있다면
		if(dbvo != null) {			
			if(!dbvo.getPassword().equals(password)) {
				dbvo = null;
			}
		}
		logger.info("loginValidation return : " + dbvo);
		return dbvo;
	}
	
	// AJAX에서 호출하는 URL에서 사용하는 메소드 
	// 1. id 중복 검사
	public String idCheck(String userId) {
		MemberVO vo = memberDAO.selectByUserId(userId);
		// 중복된 아이디가 있으면 "1", 없으면  "0"
		return vo == null ? "0" : "1";
	}
	
	// 2. 별명 중복 검사
	public String nickNameCheck(String nickName) {
		MemberVO vo = memberDAO.selectByNickName(nickName);
		return vo == null ? "0" : "1";
	}

	// 3. 소환사명 중복 검사
	public String summonerNameCheck(String summonerName) {
		logger.info("summonerNameCheck call : " + summonerName);
		MemberVO vo = memberDAO.selectBySummonerName(summonerName);
		String summonerId = "";
		if(vo != null) {
			// 가입된 소환사명
			return "1";
		} else {
			// 가입은 안되어있으나 RIOT에 등록됐는지 확인
			Gson gson = new Gson();
			String key = "RGAPI-9a9fc5b6-e54b-46a7-9ab4-c6f10f8ee47d";
			String urlAddress = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + key;
			try {
				summonerId = gson.fromJson(new InputStreamReader(new URL(urlAddress).openStream()), LinkedTreeMap.class).get("id").toString();
				System.out.println(summonerId + " &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				e.printStackTrace();
			}
			if(summonerId.equals("")) {
				// RIOT에 존재하지 않는 소환사명
				return "2";
			} else {
				// 가능한 소환사명
				return "0";
			}
		}
	}
	
	
	public void sendEmail(MemberVO memberVO) {
        MimeMessagePreparator preparator = getMessagePreparator(memberVO);
        try {
            mailSender.send(preparator);
            System.out.println("**************************** Mail Send Success ****************************");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
 
	
    private MimeMessagePreparator getMessagePreparator(final MemberVO memberVO) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom("lys7120@gmail.com");	// 인자 : memberVO.getUserId로 보내면 됨
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(memberVO.getUserId()));
                mimeMessage.setText("반갑습니다. " + memberVO.getUserName() + "님.<br>"
			                        + "회원 가입을 축하드립니다.<br> "
			                		+ "회원 가입을 완료하려면 다음의 링크를 클릭해서 인증을 완료하시기 바랍니다.<br>"
			                        + "<a href='http://localhost:8081/lol/member/confirm?userId="+memberVO.getUserId()
			                        + "&emailToken="+memberVO.getEmailToken()+"'>인증</a><br>");
                // 위의 url에 random key도 추가해서 넘기자
                mimeMessage.setSubject("[LOL자]회원 가입을 축하드립니다.");
            }
        };
        return preparator;
    }

    
	public void updateUseType(String userId, String emailToken) {
		// userId가 가진 emailToken과 쿼리스트링 emailToken이 맞으면 Update
		// 1. userId로 VO 찾아온다
		MemberVO dbvo = memberDAO.selectByUserId(userId);
		if(dbvo != null) {
			// 2. 찾은 VO에서 emailToken 값을 가져옴
			String dbToken = dbvo.getEmailToken();
			
			// 3. 2의 값과 입력 token이 같으면 update
			if(dbToken.equals(emailToken)) {
				memberDAO.updateUseType(userId);				
			}
		}
	}
}
