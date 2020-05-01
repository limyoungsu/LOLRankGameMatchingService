package kr.godz.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;

import kr.godz.vo.LeagueEntryVO;
import kr.godz.vo.SummonerChampionVO;
import kr.godz.vo.SummonerVO;

@Service
public class MatchingService {
	
	final private static Logger logger = LoggerFactory.getLogger(MatchingService.class); 
	static final String key = "RGAPI-9a9fc5b6-e54b-46a7-9ab4-c6f10f8ee47d";
	
	@SuppressWarnings("deprecation")
	public SummonerVO getSummonerInfo(String summonerName) {
		// 소환사명에는 공백이 포함될 수 있기 때문에 URL로 사용하려면 인코딩
		summonerName = URLEncoder.encode(summonerName);
		logger.info("getSummonerInfo call : " + summonerName);
		SummonerVO svo = new SummonerVO();
		SummonerChampionVO[] scvo = new SummonerChampionVO[1];
		LeagueEntryVO[] levo = new LeagueEntryVO[2];
		
		Gson gson = new Gson();
		
		String urlAddress = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+ summonerName +"?api_key=" + key;
		
		try {
			svo = gson.fromJson(new InputStreamReader(new URL(urlAddress).openStream()), SummonerVO.class);
			
			urlAddress = "https://kr.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/" + svo.getId() +"?api_key=" + key;
			scvo = gson.fromJson(new InputStreamReader(new URL(urlAddress).openStream()), SummonerChampionVO[].class);
			svo.setScvo(scvo[0]);
			
			urlAddress = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + svo.getId() +"?api_key=" + key;
			levo = gson.fromJson(new InputStreamReader(new URL(urlAddress).openStream()), LeagueEntryVO[].class);
			
			
			svo.getLevo().put(levo[0].getQueueType(), levo[0]);
			int wins = svo.getLevo().get(levo[0].getQueueType()).getWins();
			int losses = svo.getLevo().get(levo[0].getQueueType()).getLosses();
			svo.getLevo().get(levo[0].getQueueType()).setWinRate( ((double)wins / (wins + losses)) * 100 );
			if(levo.length == 2) {
				svo.getLevo().put(levo[1].getQueueType(), levo[1]);
				wins = svo.getLevo().get(levo[1].getQueueType()).getWins();
				losses = svo.getLevo().get(levo[1].getQueueType()).getLosses();
				svo.getLevo().get(levo[1].getQueueType()).setWinRate( ((double)wins / (wins + losses)) * 100 );
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info("getSummonerInfo return : " + svo);
		return svo;
	}

	@SuppressWarnings("unchecked")
	public void InitChampionList(LinkedTreeMap<Long, String[]> championList) {
		logger.info("InitChampionList call : " + championList);
		Gson gson = new Gson();
		String curr_ver = "";
		
		LinkedTreeMap<String, LinkedTreeMap<String, Object>> list = new LinkedTreeMap<String, LinkedTreeMap<String,Object>>();
		try {
			curr_ver = gson.fromJson(new InputStreamReader(new URL("https://ddragon.leagueoflegends.com/api/versions.json").openStream()), 
											String[].class)[0];
			list = (LinkedTreeMap<String, LinkedTreeMap<String, Object>>) gson.fromJson(new InputStreamReader(new URL
					("http://ddragon.leagueoflegends.com/cdn/"+curr_ver+"/data/ko_KR/champion.json").openStream()), LinkedTreeMap.class).get("data");
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			e.printStackTrace();
		}
		
		for(String key : list.keySet()) {
			long keyNum = Long.parseLong(list.get(key).get("key")+"");
			// 챔프 고유 key
			championList.put(keyNum, new String[2]);
			
			// 챔프 영문명
			championList.get(keyNum)[0] = list.get(key).get("id")+"";
			
			// 챔프 한글명
			championList.get(keyNum)[1] = list.get(key).get("name")+"";
		}
		
		logger.info("InitChampionList return : " + championList);
	}

}
