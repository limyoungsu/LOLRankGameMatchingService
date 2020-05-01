package kr.godz.vo;

import com.google.gson.internal.LinkedTreeMap;

import lombok.Data;

@Data
public class SummonerVO {
	private String name;
	
	private String accountId;
	private String id;
	private long summonerLevel;
	
	SummonerChampionVO scvo;
	
	LinkedTreeMap<String, LeagueEntryVO> levo;

	public SummonerVO() {
		this.scvo = new SummonerChampionVO();
		this.levo = new LinkedTreeMap<String, LeagueEntryVO>();
	}
}	
