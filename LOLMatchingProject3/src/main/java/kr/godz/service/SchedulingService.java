package kr.godz.service;

import org.springframework.beans.factory.annotation.Autowired;

public class SchedulingService {

	@Autowired
	BoardService boardService;
	
	public void boardAutoDelete() {
		System.out.println("게시글 자동 삭제 진행 중...");
		boardService.delete();
		System.out.println("게시글 자동 삭제 진행 완료");
		return;
	}
}
