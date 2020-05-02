package kr.godz.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import kr.godz.vo.LeagueEntryVO;
import kr.godz.vo.MatchInfoVO;
import kr.godz.vo.SummonerChampionVO;
import kr.godz.vo.SummonerVO;

@Service
public class MatchingService {
	
	final private static Logger logger = LoggerFactory.getLogger(MatchingService.class); 
	static final String key = "RGAPI-9a9fc5b6-e54b-46a7-9ab4-c6f10f8ee47d";
	static final String season = "13";			// Current Season
	static final String soloType = "420";		// Solo Rank Game 5x5 Queue Type
	static final String flexType = "440";		// Flex Rank Game 5x5 Queue Type
	static String curr_ver = "";				// Current Client Version
	
	// 서로 다른 API를 사용하는 기능을 다른 메소드로 만들어서 throws로 예외를 받자(리팩토링)
	@SuppressWarnings("deprecation")
	public SummonerVO getSummonerInfo(String summonerName) {
		// 소환사명에는 공백이 포함될 수 있기 때문에 URL로 사용하려면 인코딩
		summonerName = URLEncoder.encode(summonerName);
		logger.info("getSummonerInfo call : " + summonerName);
		SummonerVO svo = new SummonerVO();
		
		Gson gson = new Gson();
		
		try {
			// 1. Get Summoner Account Information
			svo = getSummonerAccountInfo(summonerName, gson);
			
			// 2. Get Summoner Champion Mastery (ONLY 1st place Champion)
			svo = getSummonerChampMastery(svo, gson);
			
			// 3. Get Summoner League Entry each Rank Games(SOLO OR FLEX)
			svo = getSummonerLeagueEntry(svo, gson);
			
			// 4. Get Summoner Match Games Information each Rank Games(SOLO OR FLEX)
			svo = getSummonerMatchGames(svo, gson);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info("getSummonerInfo return : " + svo);
		return svo;
	}


	// For Summoner Account Information
	public SummonerVO getSummonerAccountInfo(String summonerName, Gson gson) throws JsonSyntaxException, JsonIOException, MalformedURLException, IOException {
		logger.info("getSummonerAccountInfo call : " + summonerName);
		
		SummonerVO svo = null;
		String urlAddress = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+ summonerName +"?api_key=" + key;
		svo = gson.fromJson(new InputStreamReader(new URL(urlAddress).openStream()), SummonerVO.class);
		
		logger.info("getSummonerAccountInfo return : " + svo);
		return svo;
	}
	
	
	// For Summoner Mastery Champions
	public SummonerVO getSummonerChampMastery(SummonerVO svo, Gson gson) throws JsonSyntaxException, JsonIOException, MalformedURLException, IOException {
		logger.info("getSummonerChampMastery call : " + svo);
		
		SummonerChampionVO[] scvo = new SummonerChampionVO[1];
		String urlAddress = "https://kr.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/" + svo.getId() +"?api_key=" + key;
		scvo = gson.fromJson(new InputStreamReader(new URL(urlAddress).openStream()), SummonerChampionVO[].class);
		svo.setScvo(scvo[0]);
		
		logger.info("getSummonerChampMastery return : " + svo);
		return svo;
	}
	
		
	// For Summoner Rank Information
	public SummonerVO getSummonerLeagueEntry(SummonerVO svo, Gson gson) throws JsonSyntaxException, JsonIOException, MalformedURLException, IOException {
		logger.info("getSummonerLeagueEntry call : " + svo);

		LeagueEntryVO[] levo = new LeagueEntryVO[2];	// SOLO, FLEX ; 2Elements
		String urlAddress = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + svo.getId() +"?api_key=" + key;
		levo = gson.fromJson(new InputStreamReader(new URL(urlAddress).openStream()), LeagueEntryVO[].class);
		
		for(int i = 0; i < levo.length; i++) {
			svo.getLevo().put(levo[i].getQueueType(), levo[i]);
			int wins = svo.getLevo().get(levo[i].getQueueType()).getWins();
			int losses = svo.getLevo().get(levo[i].getQueueType()).getLosses();
			svo.getLevo().get(levo[i].getQueueType()).setWinRate( ((double)wins / (wins + losses)) * 100 );
		}	

		logger.info("getSummonerLeagueEntry return : " + svo);
		return svo;
	}


	// Solo/Flex Rank Match Game Information For Summoner
	public SummonerVO getSummonerMatchGames(SummonerVO svo, Gson gson) throws JsonSyntaxException, JsonIOException, MalformedURLException, IOException {
		logger.info("getSummonerMatchGames call : " + svo);

		// Solo Rank Match
		MatchInfoVO matchInfo = new MatchInfoVO();
		String urlAddress = "https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/" + svo.getAccountId() + "?queue=" + soloType 
						+ "&beginTime=1578596400000" + "&api_key=" + key;
		
		matchInfo = getRecentlyGames(matchInfo, gson, urlAddress);
		
		System.out.println("Data 수 : " + matchInfo.getMatches().length);
		System.out.println(matchInfo.getChampionCnt());
		System.out.println(matchInfo.getRoleCnt());
		System.out.println("신뢰도" + matchInfo.getReliability());
		
		svo.setSoloGames(matchInfo);
		
		
		
		
		// Flex Rank Match
//		matchInfo = new MatchInfoVO();
//		urlAddress = "https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/" + svo.getAccountId() + "?queue=" + flexType 
//					+ "&beginTime=1578596400000" + "&api_key=" + key;
//		
//		matchInfo = getRecentlyGames(matchInfo, gson, urlAddress);
//		
//		System.out.println("Data 수 : " + matchInfo.getMatches().length);
//		System.out.println(matchInfo.getChampionCnt());
//		System.out.println(matchInfo.getRoleCnt());
//		System.out.println("신뢰도" + matchInfo.getReliability());
//		
//		svo.setFlexGames(matchInfo);
		
		logger.info("getSummonerMatchGames return : " + svo);
		return svo;
	}
	
	
	public MatchInfoVO getRecentlyGames(MatchInfoVO matchInfo, Gson gson, String urlAddress) throws JsonSyntaxException, JsonIOException, MalformedURLException, IOException {
		logger.info("getRecentlyGames call : " + matchInfo + ", " + urlAddress);
		
		matchInfo.setMatches(gson.fromJson(new InputStreamReader(new URL(urlAddress).openStream()), MatchInfoVO.class).getMatches());
		
		// Recently 100 Games
		int totalDataCnt = matchInfo.getMatches().length;
		int inValidData = 0;
		int cnt = totalDataCnt <= 20 ? totalDataCnt : 20;
		
		// Champion Count (MAX 20 counts)
		for(int i = 0; i < cnt; i++) {
			String champId = matchInfo.getMatches()[i].getChampion() + "";
			if(matchInfo.getChampionCnt().get(champId) == null) {
				matchInfo.getChampionCnt().put(champId, 0);
			}
			matchInfo.getChampionCnt().put(champId, matchInfo.getChampionCnt().get(champId) + 1);
		}
		
		// Lane Count (MAX 100 counts)
		for(int i = 0; i < totalDataCnt; i++) {			
			// Lane Count
			String lane = matchInfo.getMatches()[i].getLane();
			switch (lane) {
			case "NONE":
				inValidData += 1;
				break;
			case "BOTTOM":
				String role = matchInfo.getMatches()[i].getRole();
				if(role.equals("DUO_SUPPORT")) {
					lane = "SUPPORT";													
				} else if(role.equals("DUO_CARRY")) {
					lane = "BOT";																		
				} else {
					inValidData += 1;
					break;
				}
			case "TOP":
			case "MID":
			case "JUNGLE":
				if(matchInfo.getRoleCnt().get(lane) == null) {
					matchInfo.getRoleCnt().put(lane, 0);
				}
				matchInfo.getRoleCnt().put(lane, matchInfo.getRoleCnt().get(lane) + 1);
				break;
			}
		}
		
		// Reliability
		matchInfo.setReliability( (int) (((double)(totalDataCnt - inValidData) / (totalDataCnt)) * 100) );
		
		// Sort keySet of Map Data
		List<String> list = new ArrayList<String>(matchInfo.getChampionCnt().keySet());
		Collections.sort(list, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return matchInfo.getChampionCnt().get(o2) - matchInfo.getChampionCnt().get(o1);
			}
		});
		
		System.out.println(list);
		matchInfo.setChampionCntList(list);
		
		// Sort keySet of Map Data
		List<String> list2 = new ArrayList<String>(matchInfo.getRoleCnt().keySet());
		Collections.sort(list2, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return matchInfo.getRoleCnt().get(o2) - matchInfo.getRoleCnt().get(o1);
			}
		});
		
		System.out.println(list2);
		matchInfo.setRoleCntList(list2);
		
		logger.info("getRecentlyGames return : " + matchInfo);
		return matchInfo;
	}

	
	@SuppressWarnings("unchecked")
	public void InitChampionList(Map<String, String[]> championList) {
		logger.info("InitChampionList call : " + championList);
		Gson gson = new Gson();
		
		Map<String, Map<String, Object>> list = new HashMap<String, Map<String,Object>>();
		try {
			curr_ver = gson.fromJson(new InputStreamReader(new URL("https://ddragon.leagueoflegends.com/api/versions.json").openStream()), 
											String[].class)[0];
			list = (Map<String, Map<String, Object>>) gson.fromJson(new InputStreamReader(new URL
					("http://ddragon.leagueoflegends.com/cdn/"+curr_ver+"/data/ko_KR/champion.json").openStream()), Map.class).get("data");
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			e.printStackTrace();
		}
		
		for(String key : list.keySet()) {
			String keyNum = list.get(key).get("key")+"";
			championList.put(keyNum, new String[2]); 					// 챔프 고유 key
			championList.get(keyNum)[0] = list.get(key).get("id")+""; 	// 챔프 영문명
			championList.get(keyNum)[1] = list.get(key).get("name")+""; // 챔프 한글명
		}
		
		logger.info("InitChampionList return : " + championList);
	}

	
	// Convert Milliseconds To Date
	public static String timestampToDate(long timestamp) {
		Date date = new Date(timestamp);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = dateFormat.format(date);
		return dateTime;
	}
	
}
