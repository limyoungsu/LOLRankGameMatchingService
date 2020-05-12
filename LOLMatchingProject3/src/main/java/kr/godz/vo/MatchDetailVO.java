package kr.godz.vo;

import lombok.Data;

@Data
public class MatchDetailVO {
	private long gameDuration;
	private int participantId;
	
	private ParticipantVO[] participants;
	private ParticipantIdentitiesVO[] participantIdentities;
	
	@Data
	public class ParticipantIdentitiesVO {
		private int participantId;
		private PlayerVO player;
		
		@Data
		public class PlayerVO {
			private String summonerId;
		}
	}
	
	@Data
	public class ParticipantVO {
		private int participantId;
		private int championId;
		private StatVO stats;
		
		@Data
		public class StatVO {
			private boolean win;
			
			private int kills;
			private int deaths;
			private int assists;
			
			private int goldEarned;
			private long totalDamageDealtToChampions;
			private int totalMinionsKilled;
		}
	}
	
}
