package com.pauloamorim.apifile.resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController()
@RequestMapping("/file/upload")
public class FileResource {
	
	private List<File> listFiles = new ArrayList<File>();

	@RequestMapping(method=RequestMethod.POST)
	public void upload(MultipartHttpServletRequest requestFile,HttpServletRequest requestPost){
		
		MultipartFile multipartFile = requestFile.getFile(requestFile.getFileNames().next());
		File file = new File(multipartFile.getOriginalFilename());
		
		//TODO REFACTORY EXCEPTION
		try {
			FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
			getListFiles().add(file);
		} catch (IOException e) {
		}
		
	}

	
	//////////////////////////////////////////////////
	//////////////////	GETTERS AND SETTERS //////////
	//////////////////////////////////////////////////
	
	
	public List<File> getListFiles() {
		return listFiles;
	}

	public void setListFiles(List<File> listFiles) {
		this.listFiles = listFiles;
	}
	
}
