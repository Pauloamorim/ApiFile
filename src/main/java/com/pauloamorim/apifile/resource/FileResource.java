package com.pauloamorim.apifile.resource;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pauloamorim.apifile.enums.StatusUploadFile;
import com.pauloamorim.apifile.pojo.ApiListingResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController()
@RequestMapping("/file")
public class FileResource {
	
	private List<ApiListingResponse> listFiles = new ArrayList<ApiListingResponse>();

	@RequestMapping(value="/upload", method=RequestMethod.POST)
	@ApiOperation(value="Upload File to server")
	@ApiResponse(code=200,message="Success")
	public void upload(MultipartHttpServletRequest requestFile,HttpServletRequest requestPost){
		LocalTime time = LocalTime.now();
		MultipartFile multipartFile = requestFile.getFile(requestFile.getFileNames().next());
		File file = new File(multipartFile.getOriginalFilename());
		
		//TODO REFACTORY EXCEPTION
		try {
			FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
			
			ApiListingResponse apiListingResponse = new ApiListingResponse();
			apiListingResponse.setFileName(multipartFile.getOriginalFilename());
			apiListingResponse.setIdentification(requestPost.getParameter("identification"));
			apiListingResponse.setStatus(StatusUploadFile.COMPLETED); //TODO
			apiListingResponse.setChunksQuantity(1); //TODO
			apiListingResponse.setSendingTime(ChronoUnit.SECONDS.between(time,LocalTime.now()));//TODO CONFIRM
			apiListingResponse.setLinkDownloadFile("");
			System.out.println(time);
			System.out.println(LocalTime.now());
			
			getListFiles().add(apiListingResponse);
		} catch (IOException e) {
		}
		System.out.println();
		
	}
	@RequestMapping(method=RequestMethod.GET) 
	@ApiOperation(value="Retrieve all file uploaded to server")
	@ApiResponse(code=200,message="Success",response=List.class)
	public ResponseEntity<List<ApiListingResponse>> listFiles(){
		return ResponseEntity.ok().body(getListFiles());
	}
	
	//////////////////////////////////////////////////
	//////////////////	GETTERS AND SETTERS //////////
	//////////////////////////////////////////////////
	
	public List<ApiListingResponse> getListFiles() {
		return listFiles;
	}

	public void setListFiles(List<ApiListingResponse> listFiles) {
		this.listFiles = listFiles;
	}
	
}
