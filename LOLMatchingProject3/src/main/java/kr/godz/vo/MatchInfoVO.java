package kr.godz.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class MatchInfoVO {
	private MatchGameVO[] matches;
	
	// total game counts each champions (key : championId, value : counts)
	private Map<String, Integer> championCnt;
	private List<String> championCntList;
	
	// total role counts each lane, role (key : TOP, MID, JUNGLE, SUPPORT, BOT)
	private Map<String, Integer> roleCnt;
	private List<String> roleCntList;
	
	// Champion Records of recently max 20 games
	private Map<String, ChampionRecordVO> championRecordMap;
	
	private int reliability;
	
	public MatchInfoVO() {
		this.matches = new MatchGameVO[50];
		this.championCnt = new HashMap<String, Integer>();
		this.roleCnt = new HashMap<String, Integer>();
		this.championRecordMap = new HashMap<String, ChampionRecordVO>();
	}
}
