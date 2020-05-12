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

import kr.godz.vo.ChampionRecordVO;
import kr.godz.vo.LeagueEntryVO;
import kr.godz.vo.MatchDetailVO;
import kr.godz.vo.MatchInfoVO;
import kr.godz.vo.SummonerChampionVO;
import kr.godz.vo.SummonerVO;

@Service
public class MatchingService {
	
	final private static Logger logger = LoggerFactory.getLogger(MatchingService.class); 
	static final int champCount = 10;
	static final String key = "RGAPI-9a9fc5b6-e54b-46a7-9ab4-c6f10f8ee47d";
	static final String beginTime = "1578596400000";
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
		
		long start = System.currentTimeMillis();
		
		SummonerVO svo = new SummonerVO();
		
		Gson gson = new Gson();
		
		try {
			// 1. Get Summoner Account Information
			long start1 = System.currentTimeMillis();
			svo = getSummonerAccountInfo(summonerName, gson);
			long end1 = System.currentTimeMillis();
			System.out.println("getSummonerAccountInfo Time : " + (double)(end1 - start1) / 1000 + " 초");
			
			// 2. Get Summoner Champion Mastery (ONLY 1st place Champion)
			long start2 = System.currentTimeMillis();
			svo = getSummonerChampMastery(svo, gson);
			long end2 = System.currentTimeMillis();
			System.out.println("getSummonerChampMastery Time : " + (double)(end2 - start2) / 1000 + " 초");
			
			// 3. Get Summoner League Entry each Rank Games(SOLO OR FLEX)
			long start3 = System.currentTimeMillis();
			svo = getSummonerLeagueEntry(svo, gson);
			long end3 = System.currentTimeMillis();
			System.out.println("getSummonerLeagueEntry Time : " + (double)(end3 - start3) / 1000 + " 초");
			
			// 4. Get Summoner Match Games Information each Rank Games(SOLO OR FLEX)
			long start4 = System.currentTimeMillis();
			svo = getSummonerMatchGames(svo, gson);
			long end4 = System.currentTimeMillis();
			System.out.println("getSummonerMatchGames Time : " + (double)(end4 - start4) / 1000 + " 초");
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();

		System.out.println("getSummonerInfo Time : " + (double)(end - start) / 1000 + " 초");
		
		logger.info("getSummonerInfo return : " + svo);
		return svo;
	}


	// For Summoner Account Information
	public SummonerVO getSummonerAccountInfo(String summonerName, Gson gson) throws JsonSyntaxException, JsonIOException, MalformedURLException, IOException {
		logger.info("getSummonerAccountInfo call : " + summonerName);
		
		SummonerVO svo = null;
		String urlAddress = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+ summonerName +"?api_key=" + key;
		svo = gson.fromJson(new InputStreamReader(new URL(urlAddress).openStream()), SummonerVO.class);
		
		logger.info("getSummonerAccountInfo return : " + svo.getAccountId() + ", " + svo.getId() + ", " + svo.getSummonerLevel());
		return svo;
	}
	
	
	// For Summoner Mastery Champions
	public SummonerVO getSummonerChampMastery(SummonerVO svo, Gson gson) throws JsonSyntaxException, JsonIOException, MalformedURLException, IOException {
		logger.info("getSummonerChampMastery call : " + svo);
		
		SummonerChampionVO[] scvo = new SummonerChampionVO[1];
		String urlAddress = "https://kr.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/" + svo.getId() +"?api_key=" + key;
		scvo = gson.fromJson(new InputStreamReader(new URL(urlAddress).openStream()), SummonerChampionVO[].class);
		svo.setScvo(scvo[0]);
		
		logger.info("getSummonerChampMastery return : " + scvo);
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

		logger.info("getSummonerLeagueEntry return : " + levo);
		return svo;
	}


	// Solo/Flex Rank Match Game Information For Summoner
	public SummonerVO getSummonerMatchGames(SummonerVO svo, Gson gson) throws JsonSyntaxException, JsonIOException, MalformedURLException, IOException {
		logger.info("getSummonerMatchGames call : " + svo);

		long soloLastGameTime = 0;
		long flexLastGameTime = 0;
		
		// Solo Rank Match
		MatchInfoVO matchInfo = new MatchInfoVO();
		String urlAddress = "https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/" + svo.getAccountId() + "?queue=" + soloType 
						+ "&beginTime=" + beginTime + "&api_key=" + key;
		
		try {
			matchInfo = getRecentlyGames(matchInfo, gson, urlAddress);
			
//			System.out.println("Data 수 : " + matchInfo.getMatches().length);
//			System.out.println(matchInfo.getChampionCnt());
//			System.out.println(matchInfo.getRoleCnt());
//			System.out.println("신뢰도" + matchInfo.getReliability());
			
			matchInfo = getChampionRecords(svo.getId() ,matchInfo, gson);
			if(matchInfo.getMatches().length != 0) {			
				soloLastGameTime = matchInfo.getMatches()[0].getTimestamp();
			}
			svo.setSoloGames(matchInfo);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		// Flex Rank Match
		matchInfo = new MatchInfoVO();
		urlAddress = "https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/" + svo.getAccountId() + "?queue=" + flexType 
					+ "&beginTime=" + beginTime + "&api_key=" + key;
		
		try {
			matchInfo = getRecentlyGames(matchInfo, gson, urlAddress);
			
//			System.out.println("Data 수 : " + matchInfo.getMatches().length);
//			System.out.println(matchInfo.getChampionCnt());
//			System.out.println(matchInfo.getRoleCnt());
//			System.out.println("신뢰도" + matchInfo.getReliability());
			
			matchInfo = getChampionRecords(svo.getId() ,matchInfo, gson);
			if(matchInfo.getMatches().length != 0) {			
				flexLastGameTime = matchInfo.getMatches()[0].getTimestamp();
			}
			svo.setFlexGames(matchInfo);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& 솔랭 : " + soloLastGameTime);
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& 자랭 : " + flexLastGameTime);		
		
		if((soloLastGameTime == 0) && (flexLastGameTime == 0)) {
			svo.setLastGameTime("기록이 없습니다.");			
		} else {
			long recentGameTime = soloLastGameTime < flexLastGameTime ? flexLastGameTime : soloLastGameTime;
			svo.setLastGameTime(timestampToDate(recentGameTime));
		}
		
		logger.info("getSummonerMatchGames return : " + svo);
		return svo;
	}
	
	
	public MatchInfoVO getChampionRecords(String summonerId, MatchInfoVO matchInfo, Gson gson) throws JsonSyntaxException, JsonIOException, MalformedURLException, IOException {
		logger.info("getChampionRecords call : " + summonerId);
		
		ChampionRecordVO crMap = null;
		
		// When Most 3 Champion < 3, array length is size od list, otherwise array length is 3
		int mostChampCnt = matchInfo.getChampionCntList().size() < 3 ? matchInfo.getChampionCntList().size() : 3; 
		List<String> most3champ = new ArrayList<String>();
		
		// Initialize
		for(int i = 0; i < mostChampCnt; i++) {
			most3champ.add(matchInfo.getChampionCntList().get(i));
			matchInfo.getChampionRecordMap().put(most3champ.get(i), new ChampionRecordVO());
		}
		
		System.out.println("모스트 3 챔프 리스트 : " + most3champ);
		
		// When Matches.length < 20, repeat count is length, otherwise repeat count is 20
		// matches 배열의 데이터가 최대 100개이지만 100개보다 작은 사람도 있음. 그 중에서도 챔프 정리에 대한 건 20개까지만 할 것
		// 즉, matches 배열의 길이가 20보다 작으면 배열 길이만큼 반복하면 되고, 20 이상이면 20으로 고정길이를 가진다.
		int matchesCnt = matchInfo.getMatches().length < champCount ? matchInfo.getMatches().length : champCount;
		for(int i = 0; i < matchesCnt; i++) {
			String gameId = matchInfo.getMatches()[i].getGameId() + "";
			String urlAddress = "https://kr.api.riotgames.com/lol/match/v4/matches/" + gameId + "?api_key=" + key;
			System.out.println(urlAddress);
			MatchDetailVO matchDvo = gson.fromJson(new InputStreamReader(new URL(urlAddress).openStream()), MatchDetailVO.class);
			
			// 한 matchId에 대해 참가자 10명에 대해
			for(int j = 0; j < matchDvo.getParticipantIdentities().length; j++) {
				// 본인 이름과 동일한 참가자의 번호(participantId) 찾기
				if(matchDvo.getParticipantIdentities()[j].getPlayer().getSummonerId().equals(summonerId)) {
					int pNum = matchDvo.getParticipantIdentities()[j].getParticipantId();
					matchDvo.setParticipantId(pNum);
					// System.out.println("참가자 번호 : " + matchDvo.getParticipantId());
					
					// 참가자 번호로 ParticipantVO에 접근, most3Champ 안에 있는 champ인지 확인 한다.
					String champId = matchDvo.getParticipants()[matchDvo.getParticipantId() - 1].getChampionId()+"";
					System.out.println("챔프 ID : " + champId);
					crMap = matchInfo.getChampionRecordMap().get(champId);
					if(most3champ.contains(champId)) {
						// 있으면 StatVO 정보를  MatchInfo의 championRecordMap으로 가져간다.
						crMap.setPlayedCnt(crMap.getPlayedCnt() + 1);
						crMap.setTotalGameDuration(crMap.getTotalGameDuration() + matchDvo.getGameDuration());
						crMap.setTotalKills(crMap.getTotalKills() + matchDvo.getParticipants()[pNum - 1].getStats().getKills());
						crMap.setTotalDeaths(crMap.getTotalDeaths() + matchDvo.getParticipants()[pNum - 1].getStats().getDeaths());
						crMap.setTotalAssists(crMap.getTotalAssists() + matchDvo.getParticipants()[pNum - 1].getStats().getAssists());
						
						crMap.setTotalGold(crMap.getTotalGold() + matchDvo.getParticipants()[pNum - 1].getStats().getGoldEarned());
						
						int winCnt = matchDvo.getParticipants()[pNum - 1].getStats().isWin() ? 1 : 0;
						crMap.setTotalWin(crMap.getTotalWin() + winCnt);
						crMap.setTotalMinionsKilled(crMap.getTotalMinionsKilled() + matchDvo.getParticipants()[pNum - 1].getStats().getTotalMinionsKilled());
						crMap.setTotalDamageDealt(crMap.getTotalDamageDealt() + matchDvo.getParticipants()[pNum - 1].getStats().getTotalDamageDealtToChampions());
						
						System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& " + champId + " &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
						System.out.println(crMap);
					} else {
						// 없으면 break, 다음 matchId로 다시 접근
						break;
					}
				}
			}
		}
		
		logger.info("getChampionRecords return : " + matchInfo.getChampionRecordMap());
		return matchInfo;
	}


	public MatchInfoVO getRecentlyGames(MatchInfoVO matchInfo, Gson gson, String urlAddress) throws JsonSyntaxException, JsonIOException, MalformedURLException, IOException {
		logger.info("getRecentlyGames call : " + matchInfo + ", " + urlAddress);
		
		matchInfo.setMatches(gson.fromJson(new InputStreamReader(new URL(urlAddress).openStream()), MatchInfoVO.class).getMatches());
		
		// Recently 100 Games
		int matchesCnt = matchInfo.getMatches().length;
		int inValidData = 0;
		int cnt = matchesCnt <= champCount ? matchesCnt : champCount;
		
		// Champion Count (MAX 20 counts)
		for(int i = 0; i < cnt; i++) {
			String champId = matchInfo.getMatches()[i].getChampion() + "";
			if(matchInfo.getChampionCnt().get(champId) == null) {
				matchInfo.getChampionCnt().put(champId, 0);
			}
			matchInfo.getChampionCnt().put(champId, matchInfo.getChampionCnt().get(champId) + 1);
		}
		
		// Lane Count (MAX 100 counts)
		for(int i = 0; i < matchesCnt; i++) {			
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
		matchInfo.setReliability( (int) (((double)(matchesCnt - inValidData) / (matchesCnt)) * 100) );
		
		// Sort keySet of Map Data
		List<String> list = new ArrayList<String>(matchInfo.getChampionCnt().keySet());
		Collections.sort(list, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return matchInfo.getChampionCnt().get(o2) - matchInfo.getChampionCnt().get(o1);
			}
		});
		
		System.out.println("챔프 빈도 : " + list);
		matchInfo.setChampionCntList(list);
		
		// Sort keySet of Map Data
		List<String> list2 = new ArrayList<String>(matchInfo.getRoleCnt().keySet());
		Collections.sort(list2, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return matchInfo.getRoleCnt().get(o2) - matchInfo.getRoleCnt().get(o1);
			}
		});
		
		System.out.println("라인 빈도 : " + list2);
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
