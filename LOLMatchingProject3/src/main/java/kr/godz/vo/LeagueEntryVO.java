package kr.godz.vo;

import lombok.Data;

@Data
public class LeagueEntryVO {
	// SOLO or FLEX인지 구별
	private String queueType;
	
	private String tier;
	private String rank;
	private int leaguePoints;
	private int wins;
	private int losses;
	private double winRate;
}
