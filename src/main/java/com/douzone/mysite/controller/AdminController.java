package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.security.Auth;
import com.douzone.security.Auth.Role;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private SiteService siteService;
	
	@Auth(Role.ADMIN)
	@RequestMapping({"","/main"})
	public String main(Model model) {
		SiteVo siteVo=siteService.getSite();
		model.addAttribute("site",siteVo);
		return "admin/main";
	}
	
	@Auth(Role.ADMIN)
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
}
