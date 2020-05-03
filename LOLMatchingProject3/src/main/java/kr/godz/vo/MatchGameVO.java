package kr.godz.vo;

import lombok.Data;

@Data
public class MatchGameVO {
	// Unique Number Of Each Match Game
	private long gameId;
	
	private int champion;
	
	// Before Game Start, Selected lane
	// TOP, MID, BOTTOM, JUNGLE, NONE
	// NONE ; DUO, DUO_SUPPORT ; NOT check
	// BOTTOM ; DUO_SUPPORT, DUO_CARRY, SOLO ; NOT check SOLO
	private String lane;
	
	private String role;
	
	// Time When Game Started
	private long timestamp;
	
	private MatchDetailVO matchDetailVO;
}
