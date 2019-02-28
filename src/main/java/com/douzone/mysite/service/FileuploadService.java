package com.douzone.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileuploadService {

	private static final String SAVE_PATH="/uploads";
	private static final String URL="/uploads/images";
	// handler
	public String restore(MultipartFile multipartFile) {
		String url = "";

		try {

			if (multipartFile.isEmpty()) {
				return url;
			}
			String originalFileName = multipartFile.getOriginalFilename();
			String extName = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
			String saveFileName = generateSaveFileName(extName);
			long filesize = multipartFile.getSize();

			System.out.println("###############" + originalFileName);
			System.out.println("###############" + extName);
			System.out.println("###############" + saveFileName);
			System.out.println("###############" + filesize);

			byte[] fileData = multipartFile.getBytes();
			OutputStream os= new FileOutputStream(SAVE_PATH+"/"+saveFileName);
			os.write(fileData);
			os.close();
			url=URL+"/"+saveFileName;
			
			
			
		} catch (IOException ex) {
			new RuntimeException("upload fail");
		}
		return url;
	}

	private String generateSaveFileName(String extName) {

		String fileName = "";
		Calendar calendar = Calendar.getInstance();

		fileName += calendar.get(calendar.YEAR);
		fileName += calendar.get(calendar.MONTH);
		fileName += calendar.get(calendar.DATE);
		fileName += calendar.get(calendar.HOUR);
		fileName += calendar.get(calendar.MINUTE);
		fileName += calendar.get(calendar.SECOND);
		fileName += calendar.get(calendar.MILLISECOND);
		fileName += ("." + extName);

		return fileName;
	}
}
