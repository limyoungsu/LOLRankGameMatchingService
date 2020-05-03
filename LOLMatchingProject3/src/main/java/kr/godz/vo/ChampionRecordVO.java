package kr.godz.vo;

import lombok.Data;

@Data
public class ChampionRecordVO {
	private int playedCnt;
	private long totalGameDuration;
	
	private int totalKills;
	private int totalDeaths;
	private int totalAssists;
	
	private int totalGold;
	
	private int totalWin;
	
	private int totalMinionsKilled;
	
	private long totalDamageDealt;
}
