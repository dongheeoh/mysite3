package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping( value="/join", method=RequestMethod.GET )
	public String join(){
		return "user/join";
	}
	
	@RequestMapping( value="/join", method=RequestMethod.POST )
	public String join( @ModelAttribute UserVo userVo ){
		userService.join( userVo );
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping( value="/login", method=RequestMethod.GET )
	public String login() {
		return "user/login";
	}
	

	@RequestMapping( "/joinsuccess" )
	public String joinsuccess(){
		return "user/joinsuccess";
	}
	
	@Auth
	@RequestMapping( value="/modify", method=RequestMethod.GET )
	public String modify(@AuthUser UserVo authUser, Model model ){
		System.out.println(authUser);
		UserVo userVo = userService.getUser( authUser.getNo() );
		model.addAttribute( "userVo", userVo );
		System.out.println(userVo);
		return "user/modify";
	}
	
	@Auth
	@RequestMapping( value="/modify", method=RequestMethod.POST )
	public String modify( @AuthUser UserVo authUser, @ModelAttribute UserVo userVo ){
		
		System.out.println(authUser);
		System.out.println(userVo);
		
		userVo.setNo( authUser.getNo() );
		userService.modifyUser( userVo );
		
		//session의 authUser 변경
		authUser.setName(userVo.getName());
		
		return "redirect:/user/modify?result=success";
	}
}
