package kr.godz.vo;
// 페이징 처리를 하는 VO 어떤 리스트든 페이징을 할 수 있도록 Generic으로 만들자!!!

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
public class PagingVO<T> {
	private List<T> list; // 페이징된 객체를 저장할 리스트
	
	// 4개는 생성자에서 넘겨 받는다.
	private int totalCount; 	// 전체 개수
	private int currentPage;	// 현재 페이지
	private int pageSize;		// 페이지당 글 수
	private int blockSize;		// 하단의 페이지 리스트 개수
	
	// 나머지는 계산한다.
	private int totalPage;		// 전체 페이지 수
	private int startNo;		// 시작 글번호
	private int endNo;			// 끝 글번호(오라클에서 사용)
	private int startPage;		// 시작 페이지 번호
	private int endPage;		// 끝 페이지 번호
	
	// 생성자 : 계산되지 않고 결정해 주어야 하는것은 넘겨 받는다.
	public PagingVO(int totalCount, int currentPage, int pageSize, int blockSize) {
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.blockSize = blockSize;
		calc(); // 나머지 값을 계산해줄 메서드
		System.out.println("전체 페이지 : " + totalPage);
		System.out.println("시작 페이지 : " + startPage);
		System.out.println("끝 페이지" + endPage);
	}
	private void calc() {
		// 유효성 검사
		if(currentPage<=0) currentPage=1;
		if(pageSize<=0) pageSize=10;
		if(blockSize<=0) blockSize=5;
		// 데이터가 있을 경우에만 계산
		if(totalCount>0) { 
			// 전체페이지 = (개수-1)/페이지당글수 + 1;
			totalPage = (totalCount-1)/pageSize + 1;
			// 현재페이지가 전체 페이지보다 클수 없다.
			if(currentPage>totalPage) { 
				currentPage = 1; 
			}
			// 시작 글번호 = (현재페이지-1)*페이지당글수 (오라클의 경우는 +1을 해준다)
			startNo = (currentPage-1) * pageSize + 1;
			// 끝 글번호 = 시작글번호 + 페이지당 글수 - 1
			endNo = startNo + pageSize -1;
			// 마지막 페이지의 마지막번호 처리
			if(endNo>=totalCount) endNo = totalCount; // 오라클은 -1을 하지 않는다.
			// 시작페이지번호 = (현재페이지번호-1)/블록사이즈 * 블록사이즈 + 1
			startPage = (currentPage-1)/blockSize * blockSize + 1;
			// 끝페이지 번호 = 시작페이지번호+블록사이즈 -1
			endPage = startPage + blockSize -1;
			// 마지막 페이지 번호가 전체 페이지 번호보다 클 수 없다.
			if(endPage>totalPage) endPage = totalPage;
		}
	}

	// getter/setter를 만든다.
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getTotalCount() {
		return totalCount;
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
	public int getTotalPage() {
		return totalPage;
	}
	public int getStartNo() {
		return startNo;
	}
	public int getEndNo() {
		return endNo;
	}
	public int getStartPage() {
		return startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	
	// 상단의 "전체 : 1개(0/0Page)"를 문자열로 리턴하는 메서드 추가!!!!
	public String getPageInfo() {
		String info = "전체 : " + totalCount + "개";
		if(totalCount>0) {
			info += "(" + currentPage + "/" + totalPage + "Page)";
		}
		return info;
	}
	
	
	// 하단의 페이지번호 리스트를 문자열로 리턴하는 메서드 추가!!!!
	public String getPageList(BoardVO boardVO) {
		String pageList = "";
		String searchStr = "";
		if(boardVO.getTier() != null) {
			searchStr = "&tier="+boardVO.getTier()+"&division="+boardVO.getDivision()+"&queueType="+boardVO.getQueueType()+
					"&lane="+boardVO.getLane()+"&expectedTime="+boardVO.getExpectedTime()+"&isVoice="+boardVO.getIsVoice();
		}
		if(totalPage>0) {
			pageList += "<ul class='pagination pagination-sm justify-content-center'>";
			// 시작페이지번호가 1보다 크다면 "이전"이 있다.
			if(startPage>1) { 
				pageList +="<li class='page-item'>";
				pageList +="<a class='page-link' href='?p="+(startPage-1)+"&s="+pageSize+"&b="+blockSize+searchStr+"'>";
				pageList +="이전";
				pageList +="</a>";
				pageList +="</li>";
			}
			// 페이지 번호들 출력
			for(int i=startPage;i<=endPage;i++) {
				if(i==currentPage) {
					pageList += "<li class='page-item active'>";
					pageList +="<a class='page-link'>";
					pageList += i;
					pageList +="</a>";
					pageList += "</li>";
				}else {
					pageList += "<li class='page-item'>";
					pageList +="<a class='page-link' href='?p="+(i)+"&s="+pageSize+"&b="+blockSize+searchStr+"'>";
					pageList += i;
					pageList +="</a>";
					pageList += "</li>";
				}
			}
			// 마지막페이지번호가 전체페이지수 보다 적다면 "다음"이 있다.
			if(endPage<totalPage) { 
				pageList +="<li class='page-item'>";
				pageList +="<a class='page-link' href='?p="+(endPage+1)+"&s="+pageSize+"&b="+blockSize+searchStr+"'>";
				pageList +="다음";
				pageList +="</a>";
				pageList +="</li>";
			}
			pageList += "</ul>";
		}
		return pageList;
	}
	
	
	public String getPageTextList() {
		String pageList = "";
		if(totalPage>0) {
			pageList += "";
			// 시작페이지번호가 1보다 크다면 "이전"이 있다.
			if(startPage>1) { 
				pageList +="[<a href='?p="+(startPage-1)+"&s="+pageSize+"&b="+blockSize+"'>";
				pageList +="이전";
				pageList +="</a>] ";
			}
			// 페이지 번호들 출력
			for(int i=startPage;i<=endPage;i++) {
				if(i==currentPage) {
					pageList += "["+i+"] ";
				}else {
					pageList +="[<a href='?p="+(i)+"&s="+pageSize+"&b="+blockSize+"'>";
					pageList += i;
					pageList +="</a>] ";
				}
			}
			// 마지막페이지번호가 전체페이지수 보다 적다면 "다음"이 있다.
			if(endPage<totalPage) { 
				pageList +=" [<a href='?p="+(endPage+1)+"&s="+pageSize+"&b="+blockSize+"'>";
				pageList +="다음]";
				pageList +="</a>";
			}
			pageList += "</ul>";
		}
		return pageList;
	}
	
	
	public String getPageImageList() {
		String pageList = "";
		if(totalPage>0) {
			pageList += "";
			// 시작페이지번호가 1보다 크다면 "이전"이 있다.
			if(startPage>1) { 
				pageList +="<a href='?p=1&s="+pageSize+"&b="+blockSize+"'>";
				pageList +="<img src='images/first24.png'>";
				pageList +="</a> ";
				pageList +="<a href='?p="+(startPage-1)+"&s="+pageSize+"&b="+blockSize+"'>";
				pageList +="<img src='images/left24.png'>";
				pageList +="</a> ";
			}
			// 페이지 번호들 출력
			for(int i=startPage;i<=endPage;i++) {
				if(i==currentPage) {
					pageList += "["+i+"] ";
				}else {
					pageList +="[<a href='?p="+(i)+"&s="+pageSize+"&b="+blockSize+"'>";
					pageList += i;
					pageList +="</a>] ";
				}
			}
			// 마지막페이지번호가 전체페이지수 보다 적다면 "다음"이 있다.
			if(endPage<totalPage) { 
				pageList +="<a href='?p="+(endPage+1)+"&s="+pageSize+"&b="+blockSize+"'>";
				pageList +="<img src='images/right24.png'>";
				pageList +="</a> ";
				pageList +="<a href='?p="+totalPage+"&s="+pageSize+"&b="+blockSize+"'>";
				pageList +="<img src='images/last24.png'>";
				pageList +="</a> ";
			}
			pageList += "</ul>";
		}
		return pageList;
	}
	
	
	//----------------------------------------------------------------------------------------------------
	// POST 전송
	public String getPageListPost(BoardVO boardVO) {
		String pageList = "";
		String searchStr = "";
		if(boardVO.getTier() != null) {
			searchStr = ",\"tier\":\""+boardVO.getTier()+"\",\"division\":\""+boardVO.getDivision()+"\",\"queueType\":\""+boardVO.getQueueType()+"\",\"lane\":\""+boardVO.getLane()+"\",\"expectedTime\":\""+boardVO.getExpectedTime()+"\",\"isVoice\":\""+boardVO.getIsVoice()+"\"";
		}
		if(totalPage>0) {
			pageList += "<ul class='pagination pagination-sm justify-content-center'>";
			// 시작페이지번호가 1보다 크다면 "이전"이 있다.
			if(startPage>1) { 
				pageList +="<li class='page-item'>";
				pageList +="<a class='page-link'  href='#' onclick='post_to_url(\"?\",{\"p\":\""+(1)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\""+searchStr+"})'>";
				pageList +="처음";
				pageList +="</a>";
				pageList +="</li>";
				pageList +="<li class='page-item'>";
				pageList +="<a class='page-link'  href='#' onclick='post_to_url(\"?\",{\"p\":\""+(startPage-1)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\""+searchStr+"})'>";
				pageList +="이전";
				pageList +="</a>";
				pageList +="</li>";
			}
			// 페이지 번호들 출력
			for(int i=startPage;i<=endPage;i++) {
				if(i==currentPage) {
					pageList += "<li class='page-item active'>";
					pageList +="<a class='page-link'>";
					pageList += i;
					pageList +="</a>";
					pageList += "</li>";
				}else {
					pageList += "<li class='page-item'>";
					pageList +="<a class='page-link' href='#' onclick='post_to_url(\"?\",{\"p\":\""+(i)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\""+searchStr+"})'>";
					pageList += i;
					pageList +="</a>";
					pageList += "</li>";
				}
			}
			// 마지막페이지번호가 전체페이지수 보다 적다면 "다음"이 있다.
			if(endPage<totalPage) { 
				pageList +="<li class='page-item'>";
				pageList +="<a class='page-link'  href='#' onclick='post_to_url(\"?\",{\"p\":\""+(endPage+1)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\""+searchStr+"})'>";
				pageList +="다음";
				pageList +="</a>";
				pageList +="</li>";
				pageList +="<li class='page-item'>";
				pageList +="<a class='page-link'  href='#' onclick='post_to_url(\"?\",{\"p\":\""+(totalPage)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\""+searchStr+"})'>";
				pageList +=" 끝 ";
				pageList +="</a>";
				pageList +="</li>";
			}
			pageList += "</ul>";
		}
		return pageList;
	}
	
	
	public String getPageTextListPost() {
		String pageList = "";
		if(totalPage>0) {
			pageList += "";
			// 시작페이지번호가 1보다 크다면 "이전"이 있다.
			if(startPage>1) { 
				pageList +="[<a href='#' onclick='post_to_url(\"?\",{\"p\":\""+(1)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\"})'>";
				pageList +="처음";
				pageList +="</a>] ";
				pageList +="[<a href='#' onclick='post_to_url(\"?\",{\"p\":\""+(startPage-1)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\"})'>";
				pageList +="이전";
				pageList +="</a>] ";
			}
			// 페이지 번호들 출력
			for(int i=startPage;i<=endPage;i++) {
				if(i==currentPage) {
					pageList += "["+i+"] ";
				}else {
					pageList +="[<a href='#' onclick='post_to_url(\"?\",{\"p\":\""+(i)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\"})'>";
					pageList += i;
					pageList +="</a>] ";
				}
			}
			// 마지막페이지번호가 전체페이지수 보다 적다면 "다음"이 있다.
			if(endPage<totalPage) { 
				pageList +="[<a href='#' onclick='post_to_url(\"?\",{\"p\":\""+(endPage+1)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\"})'>";
				pageList +="다음] ";
				pageList +="</a>";
				pageList +="[<a href='#' onclick='post_to_url(\"?\",{\"p\":\""+(totalPage)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\"})'>";
				pageList +=" 끝 ]";
				pageList +="</a>";
			}
			pageList += "</ul>";
		}
		return pageList;
	}
	
	
	public String getPageImageListPost() {
		String pageList = "";
		if(totalPage>0) {
			pageList += "";
			// 시작페이지번호가 1보다 크다면 "이전"이 있다.
			if(startPage>1) { 
				pageList +="<a  href='#' onclick='post_to_url(\"?\",{\"p\":\""+(1)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\"})'>";
				pageList +="<img src='images/first18.png'>";
				pageList +="</a> ";
				pageList +="<a href='#' onclick='post_to_url(\"?\",{\"p\":\""+(startPage-1)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\"})'>";
				pageList +="<img src='images/left18.png'>";
				pageList +="</a> ";
			}
			// 페이지 번호들 출력
			for(int i=startPage;i<=endPage;i++) {
				if(i==currentPage) {
					pageList += "["+i+"] ";
				}else {
					pageList +="[<a href='#' onclick='post_to_url(\"?\",{\"p\":\""+(i)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\"})'>";
					pageList += i;
					pageList +="</a>] ";
				}
			}
			// 마지막페이지번호가 전체페이지수 보다 적다면 "다음"이 있다.
			if(endPage<totalPage) { 
				pageList +="<a href='#' onclick='post_to_url(\"?\",{\"p\":\""+(endPage+1)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\"})'>";
				pageList +="<img src='images/right18.png'>";
				pageList +="</a> ";
				pageList +="<a  href='#' onclick='post_to_url(\"?\",{\"p\":\""+(totalPage)+"\",\"s\":\""+pageSize+"\",\"b\":\""+blockSize+"\"})'>";
				pageList +="<img src='images/last18.png'>";
				pageList +="</a> ";
			}
			pageList += "</ul>";
		}
		return pageList;
	}

	@Override
	public String toString() {
		return "PagingVO [list=" + list + ", totalCount=" + totalCount + ", currentPage=" + currentPage + ", pageSize="
				+ pageSize + ", blockSize=" + blockSize + ", totalPage=" + totalPage + ", startNo=" + startNo
				+ ", endNo=" + endNo + ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}
}