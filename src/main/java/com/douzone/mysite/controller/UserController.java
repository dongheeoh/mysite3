package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService ;
	
	
		@RequestMapping(value="/join", method=RequestMethod.GET)
		public String join() {
			return "/user/join";
		}

		@RequestMapping(value="/join", method=RequestMethod.POST)
		public String join(@ModelAttribute UserVo userVo) {
			userService.join(userVo);//이게 비즈니스지
			return "redirect:/user/joinsuccess";
		}
	
		
		@RequestMapping("/joinsuccess")
		public String joinSuccess() {
			return "/user/joinsuccess";
		}
	
		@RequestMapping(value="/login", method=RequestMethod.GET)
		public String login(HttpSession session) { //파라미터임 즉 getsession해서 던져준거임
			//로그인한사람은 들어오면안됨
			UserVo authUser = (UserVo)session.getAttribute("authuser");
			if(authUser!= null){		
				return "redirect:/main";
			}
			return "/user/login";
		}
	
		@RequestMapping(value="/login", method=RequestMethod.POST)
		public String login(HttpSession session, Model model,@ModelAttribute UserVo userVo) {

			//로그인한사람은 보내면안됨
			UserVo authUser = (UserVo)session.getAttribute("authuser");
			if(authUser!= null){		
				return "redirect:/main";
			}
			
			authUser = userService.login(userVo);		
			
			if(authUser == null){		
				model.addAttribute("result",  "fail");
				return "redirect:/user/login";
			}
			
			session.setAttribute("authuser", authUser); //세션에 등록
			return "redirect:/main";
		}
	
		@RequestMapping(value="/logout", method=RequestMethod.GET)
		public String logout(HttpSession session) {
			userService.logout(session);
			return "redirect:/main";
		}
	
		@RequestMapping(value="/modify", method=RequestMethod.GET)
		public String modify(HttpSession session, Model model) {
			
			UserVo authUser = (UserVo)session.getAttribute("authuser");
			if(authUser == null){		
				return "redirect:/main";
			}
			authUser = userService.modifyform(authUser.getNo());
			model.addAttribute("vo", authUser);
			return "user/modify";
		}
		
		@RequestMapping(value="/modify", method=RequestMethod.POST)
		public String modify(HttpSession session, UserVo uservo) {
			
			UserVo authUser = (UserVo)session.getAttribute("authuser");
			if(authUser == null){		
				return "redirect:/main";
			}
			uservo.setNo(authUser.getNo());
			userService.modify(uservo);
			
			authUser.setName(uservo.getName());		//세션수정
			session.setAttribute("authuser", authUser); 
			return "redirect:/main";
		}
}
