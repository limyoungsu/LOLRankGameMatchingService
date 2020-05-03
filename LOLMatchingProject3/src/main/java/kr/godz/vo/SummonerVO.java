package kr.godz.vo;

import com.google.gson.internal.LinkedTreeMap;

import lombok.Data;

@Data
public class SummonerVO {
	// Summoner Name In LOL
	private String name;
	
	// 1. Summoner Account Information
	private String accountId;
	private String id;
	private long summonerLevel;
	
	// 2. Most Used Champion Information
	SummonerChampionVO scvo;
	
	// 3. Ranked Solo//flex information
	LinkedTreeMap<String, LeagueEntryVO> levo;

	// 4. Solo Rank Match Game played Information(420)
	MatchInfoVO soloGames;
	
	// 4. Flex Rank Match Game played Information(440)
	MatchInfoVO flexGames;
	
	
	public SummonerVO() {
		this.levo = new LinkedTreeMap<String, LeagueEntryVO>();
	}
}	
