package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	public Map<String, Object> list(String spage,String start) {

//		// isNumeric [a-zA-Z0-9]* *-> 길이 제한없이 d-> 숫자문자
//		if (spage.matches("\\d*") == false)
//			spage = "1";
		if (start==null)
		{
			start="1";
		}
		int page = Integer.parseInt(spage);
		int toTalPageCount = boardDao.BoardCount();

		List<BoardVo> list = boardDao.getList(page);
		Map<String, Object> map = new HashMap<String, Object>();

		// 시작 페이지
		int bStart = 0;
		// 끝 페이지
		int bEnd = 0;
		if (toTalPageCount % 10 == 0)
			bEnd = toTalPageCount / 10;
		else
			bEnd = toTalPageCount / 10 + 1;
//		if (Integer.parseInt(spage) % 6 != 0)
//			bStart = Integer.parseInt(spage) - (Integer.parseInt(spage) % 6) + 1;
//		else
			if(Integer.parseInt(start)!=1)
			bStart = Integer.parseInt(spage) - (Integer.parseInt(spage) % Integer.parseInt(start));
			else
				bStart=1;
			if(bStart==0)
				bStart=1;
			
			
			

		map.put("list", list);
		map.put("pagerTotalPageCount", toTalPageCount);
		map.put("bStart", bStart);
		map.put("bEnd", bEnd);

		return map;
	}

	public Map<String, Object> searchList(String spage,String searchValue,String text,String start) {

//		// isNumeric [a-zA-Z0-9]* *-> 길이 제한없이 d-> 숫자문자
		if (spage.matches("\\d*") == false)
			spage = "1";
		if (start==null)
		{
			start="1";
		}
		//몇번 페이지를 구하는지
		int page = Integer.parseInt(spage);
		
		//전체 페이지 수
		int toTalPageCount = boardDao.searchCount(text, searchValue);
		
		//검색 결과
		List<BoardVo> list = boardDao.searchGetList(text, searchValue, page);

		//결과 반환할 map
		Map<String, Object> map = new HashMap<String, Object>();

		// 시작 페이지
		int bStart = 0;
		// 끝 페이지
		int bEnd = 0;
		
		//총 개수에 10개씩이니깐 끝 페이지 저장 알고리즘
		if (toTalPageCount % 10 == 0)
			bEnd = toTalPageCount / 10;
		else
			bEnd = toTalPageCount / 10 + 1;
		
		if(Integer.parseInt(start)!=1)
			bStart = Integer.parseInt(spage) - (Integer.parseInt(spage) % Integer.parseInt(start));
			else
				bStart=1;
			if(bStart==0)
				bStart=1;
		//마찬가지로 시작페이지 계산 알고리즘  게시판은 5페이지씩임.
		map.put("list", list);
		map.put("pagerTotalPageCount", toTalPageCount);
		map.put("bStart", bStart);
		map.put("bEnd", bEnd);

		return map;
	}

	public Map<String, Object> view(String spage,String boardNo,String boolcomment,String start) {

		if(spage==null)
			spage="1";
		
		if (start==null)
		{
			start="1";
		}
		int toTalPageCount= new BoardDao().CommentCount(Integer.parseInt(boardNo));
		// 시작 페이지
				int bStart = 0;
				// 끝 페이지
				int bEnd = 0;
				if (toTalPageCount % 10 == 0)
					bEnd = toTalPageCount / 10;
				else
					bEnd = toTalPageCount / 10 + 1;
//				if (Integer.parseInt(spage) % 6 != 0)
//					bStart = Integer.parseInt(spage) - (Integer.parseInt(spage) % 6) + 1;
//				else
					if(Integer.parseInt(start)!=1)
					bStart = Integer.parseInt(spage) - (Integer.parseInt(spage) % Integer.parseInt(start));
					else
						bStart=1;
					if(bStart==0)
						bStart=1;
		int page  = Integer.parseInt(spage);

		BoardVo vo = new BoardDao().view(boardNo);
		
		//hit
		if(boolcomment==null) //처음 들어왔고 댓글적는게 아닐경우
		{
			boardDao.hit(boardNo);
		}
		List <CommentVo> list = boardDao.getCommentList(page, Integer.parseInt(boardNo));
		//결과 반환할 map
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("boardVo", vo);
		map.put("pagerTotalPageCount", toTalPageCount);
		map.put("bStart", bStart);
		map.put("bEnd", bEnd);
		map.put("page", page);
		map.put("list", list);

		return map;
	}

	public boolean write(BoardVo vo,Long userNo) {
		
		boolean result =boardDao.write(vo, userNo);
		return result;
	}
	public boolean delete(String no) {
		boolean result = boardDao.delete(no);
		return result;
	}
	public boolean modify(BoardVo boardVo) {
		boolean result =boardDao.modify(boardVo.getNo(), boardVo.getTitle(), boardVo.getContents());
		return result;
	}
	public BoardVo modifyShow(String no) {
		BoardVo boardVo =boardDao.view(no);
		return boardVo;
	}
	public void reply(BoardVo boardVo,Long userNo) {
		boardDao.replyWrite(boardVo, userNo);
	}
	public void comment(String contents,Long userNo,Long boardNo) {
		boardDao.commentWrite(contents, userNo, boardNo);
	}
	public void commentDelete(String commentNo) {
		boardDao.commentDelete(commentNo);
	}
}
