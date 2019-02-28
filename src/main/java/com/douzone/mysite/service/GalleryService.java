package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GalleryDao;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	
	@Autowired
	private GalleryDao galleryDao;
	
	public List<GalleryVo> getGalleryList() {
		return galleryDao.getGalleryList();
	}
	
	public void imageUpload( GalleryVo galleryVo ) {
		galleryDao.insert( galleryVo );
	}
	
	public int deleteImage( long no ) {
		System.out.println(no);
		return galleryDao.delete( no );
		
	}
}
