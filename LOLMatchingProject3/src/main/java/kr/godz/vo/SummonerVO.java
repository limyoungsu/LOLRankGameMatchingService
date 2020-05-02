package kr.godz.vo;

import com.google.gson.internal.LinkedTreeMap;

import lombok.Data;

@Data
public class SummonerVO {
	// Summoner Name In LOL
	private String name;
	
	// Summoner Account Information
	private String accountId;
	private String id;
	private long summonerLevel;
	
	// Most Used Champion Information
	SummonerChampionVO scvo;
	
	// Ranked Solo//flex information
	LinkedTreeMap<String, LeagueEntryVO> levo;

	// Solo Rank Match Game played Information(420)
	MatchInfoVO soloGames;
	
	// Flex Rank Match Game played Information(440)
	MatchInfoVO flexGames;
	
	
	public SummonerVO() {
		this.levo = new LinkedTreeMap<String, LeagueEntryVO>();
	}
}	
