package kr.godz.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {
	private String nickName;
	private String summonerName;
	private String title;
	private String content;
	private Date regDate;
	
	private String tier;
	private String division;
	private String lane;
	private String queueType;
	
	private String expectedTime;
	private String isVoice;
}
