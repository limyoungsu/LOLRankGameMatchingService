package kr.godz.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// include.jsp에 있는 변수들을 VO로 따로 만든다.
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CommVO {
	
	// 요청으로부터 받는 변수의 이름
	// 브라우저로부터 요청값이 없는 경우 초기값 설정
	private int p = 1;
	private int s = 10;
	private int b = 10;
	private int m = 0;
	
	// 요청으로부터 받는 이름이 아닌 실제로 사용하는 변수의 이름
	// idx경우 양쪽 다 이름이 같기 때문에 한 쪽에만 선언
	@XmlElement
	private int idx = -1;
	
	@XmlElement
	private int currentPage = 1;
	
	@XmlElement
	private int pageSize = 10;
	
	@XmlElement
	private int blockSize = 10;
	
	@XmlElement
	private int mode = 0;
	
	// getter
	public int getP() {
		return p;
	}
	public int getS() {
		return s;
	}
	public int getB() {
		return b;
	}
	public int getIdx() {
		return idx;
	}
	public int getM() {
		return m;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public int getBlockSize() {
		return blockSize;
	}
	public int getMode() {
		return mode;
	}

	// Integer.parseInt(request.getPatameter()) 에 대한 작업을 대체
	// p,s,b,idx,m을 받을 때 나머지 변수(currentPage, pageSize, blockSize, mode)도 초기화 시켜주는 것을 setter로 처리
	public void setP(int p) {
		this.p = p;
		currentPage = this.p;
	}
	public void setS(int s) {
		this.s = s;
		pageSize = this.s;
	}
	public void setB(int b) {
		this.b = b;
		blockSize = this.s;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public void setM(int m) {
		this.m = m;
		mode = this.m;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	
	@Override
	public String toString() {
		return "CommVO [p=" + p + ", s=" + s + ", b=" + b + ", idx=" + idx + ", m=" + m + ", currentPage=" + currentPage
				+ ", pageSize=" + pageSize + ", blockSize=" + blockSize + ", mode=" + mode + "]";
	}
}
