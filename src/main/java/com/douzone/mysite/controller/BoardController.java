package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.util.WebUtil;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;


@Controller
@RequestMapping( "/board" )
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String index(
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword,
		Model model ) {
		
		Map<String, Object> map = boardService.getMessageList( page, keyword );
		model.addAttribute( "map", map );
		
		return "board/list";
	}
	
	@RequestMapping( "/view/{no}" )
	public String view( @PathVariable( "no" ) Long no, Model model ) {
		BoardVo boardVo = boardService.getMessage( no );
		model.addAttribute( "boardVo", boardVo );
		return "board/view";
	}
	
	@RequestMapping( "/delete/{no}" )
	public String delete(
		HttpSession session,
		@PathVariable( "no" ) Long boardNo,
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword ) {

		UserVo authUser = (UserVo)session.getAttribute("authUser");

		/* 접근제어 */
		if(null == authUser) {
			return "redirect:/";
		}
		
		boardService.deleteMessage( boardNo, authUser.getNo() );
		return "redirect:/board?p=" + page + "&kwd=" + WebUtil.encodeURL( keyword, "UTF-8" );
	}
	
	@RequestMapping( value="/modify/{no}" )	
	public String modify(
		HttpSession session,
		@PathVariable( "no" ) Long no,
		Model model) {

		UserVo authUser = (UserVo)session.getAttribute("authUser");

		/* 접근제어 */
		if(null == authUser) {
			return "redirect:/";
		}
		
		BoardVo boardVo = boardService.getMessage(no, authUser.getNo() );
		model.addAttribute( "boardVo", boardVo );
		return "board/modify";
	}

	@RequestMapping( value="/modify", method=RequestMethod.POST )	
	public String modify(
		HttpSession session,
		@ModelAttribute BoardVo boardVo,
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword ) {

		UserVo authUser = (UserVo)session.getAttribute("authUser");

		/* 접근제어 */
		if(null == authUser) {
			return "redirect:/";
		}
		
		boardVo.setUserNo( authUser.getNo() );
		boardService.modifyMessage( boardVo );
		return "redirect:/board/view/" + boardVo.getNo() + 
				"?p=" + page + 
				"&kwd=" + WebUtil.encodeURL( keyword, "UTF-8" );
	}
	
	@RequestMapping( value="/write", method=RequestMethod.GET )	
	public String write(HttpSession session) {
		/* 접근제어 */
		if(null == session.getAttribute("authUser")) {
			return "redirect:/";
		}
		
		return "board/write";
	}

	@RequestMapping( value="/write", method=RequestMethod.POST )	
	public String write(
		HttpSession session,
		@ModelAttribute BoardVo boardVo,
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword ) {

		UserVo authUser = (UserVo)session.getAttribute("authUser");

		/* 접근제어 */
		if(null == authUser) {
			return "redirect:/";
		}
		
		boardVo.setUserNo( authUser.getNo() );
		
		if( boardVo.getGroupNo() != null ) {
			boardService.increaseGroupOrderNo( boardVo );
		}
		boardService.addMessage( boardVo );
		
		return	( boardVo.getGroupNo() != null ) ?
				"redirect:/board?p=" + page + "&kwd=" + WebUtil.encodeURL( keyword, "UTF-8" ) :
				"redirect:/board";
	}

	@RequestMapping( value="/reply/{no}" )	
	public String reply(
		HttpSession session,
		@PathVariable( "no" ) Long no,
		Model model) {

		/* 접근제어 */
		if(null == session.getAttribute("authUser")) {
			return "redirect:/";
		}

		BoardVo boardVo = boardService.getMessage( no );
		boardVo.setOrderNo( boardVo.getOrderNo() + 1 );
		boardVo.setDepth( boardVo.getDepth() + 1 );
		
		model.addAttribute( "boardVo", boardVo );
		
		return "board/reply";
	}	
}