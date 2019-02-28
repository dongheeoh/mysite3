package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.SiteDao;
import com.douzone.mysite.vo.SiteVo;

@Service
public class SiteService {
	@Autowired
	private SiteDao siteDao;
	
	public SiteVo getSite() {
		return siteDao.getSite();
	}
	
	public void update(SiteVo siteVo) {
		siteDao.mainUpdate(siteVo);
	}
}
