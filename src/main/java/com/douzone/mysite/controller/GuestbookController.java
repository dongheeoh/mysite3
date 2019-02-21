package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping({"","/list"})
	public String list(Model model) {
		guestbookService.list(model);
		return "/guestbook/list";
	}
	
	@RequestMapping("/deletefrom")
	public String form(@RequestParam(value="no",required=false) Long no) {
		return "/guestbook/deleteform";
	}
	
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public String add(GuestBookVo guestbookVo) {
		guestbookService.insert(guestbookVo);
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String delete(GuestBookVo guestbookVo) {
		guestbookService.delete(guestbookVo);
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping("/ajax")
	public String ajax(Model model) {
		guestbookService.list(model);
		return "/guestbook/index-ajax";
	}
}
