package com.douzone.mysite.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.dto.JsonResult;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.mysite.vo.UserVo;

@Controller
public class MainController {

	@Autowired
	private SiteService siteService;
	
	@RequestMapping({"","/main"})
	public String main(Model model, HttpSession session) {
		//return "/WEB-INF/views/main/index.jsp";
		SiteVo siteVo=siteService.getSite();
		model.addAttribute("site",siteVo);
		session.setAttribute("siteTitle", siteVo.getTitle());
		return "/main/index";
	}
	
	@ResponseBody
	@RequestMapping("/hello")
	public UserVo hello() {
		return new UserVo();
	}
	
	@ResponseBody
	@RequestMapping("/hello2")
	public JsonResult hello2() {

		return JsonResult.success(new UserVo());
	}
}
