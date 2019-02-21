package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.douzone.mysite.repository.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;

@Service
public class GuestbookService {
	@Autowired //주입해주라고
	private GuestBookDao guestbookDao;
	
	public void list(Model model) {
		model.addAttribute("list",guestbookDao.getList());
	}
	public void insert(GuestBookVo guestbookVo) {
		guestbookDao.insert(guestbookVo);
	}
	public void delete(GuestBookVo guestbookVo) {
		guestbookDao.delete(guestbookVo);
	}
	
}
