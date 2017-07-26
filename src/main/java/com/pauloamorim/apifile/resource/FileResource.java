package com.pauloamorim.apifile.resource;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pauloamorim.apifile.Util;
import com.pauloamorim.apifile.enums.StatusUploadFile;
import com.pauloamorim.apifile.exception.handler.HandlerException;
import com.pauloamorim.apifile.pojo.ApiListingResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/file")
public class FileResource {
	private Map<String, ApiListingResponse> map = new HashMap<String, ApiListingResponse>();
	private List<ApiListingResponse> listFiles = new ArrayList<ApiListingResponse>();
	private static final String linkDownload = "localhost:8080/file/download/";

	/**
	 * @param requestFile
	 * @throws IOException The Excpetion is captures in Class {@link HandlerException}
	 */
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	@ApiOperation(value="Upload File to server")
	@ApiResponse(code=200,message="Success")
	public void upload(MultipartHttpServletRequest requestFile) throws IOException{
		long start = System.nanoTime();
		
		//get the file from request
		MultipartFile multipartFile = requestFile.getFile(requestFile.getFileNames().next());
		String fileName = multipartFile.getOriginalFilename();
		
		//create an instance from multipart file
		File file = new File(fileName);
		
		createFileInServer(requestFile, start, multipartFile, fileName, file);
	}
	@RequestMapping(method=RequestMethod.GET) 
	@ApiOperation(value="Retrieve all file uploaded to server")
	@ApiResponse(code=200,message="Success",response=List.class)
	public ResponseEntity<Collection<ApiListingResponse>> listFiles(){
		Collection<ApiListingResponse> listCopy = Collections.unmodifiableCollection(getMap().values());
		return ResponseEntity.ok().body(listCopy);
	}
	
	
	private void createFileInServer(MultipartHttpServletRequest requestFile, long start, MultipartFile multipartFile,
			String fileName, File file) throws IOException {
		//write the content in file
		FileUtils.writeByteArrayToFile(file, multipartFile.getBytes(),true);
		
		BigDecimal fileSize = getContenteRange(requestFile);
		BigDecimal rangeSended = getRangeSended(requestFile);
		
		controlsInformationInHashMap(requestFile, start, fileName, file);
			
		
		//if true, is the last part of file
		if(fileSize.equals(rangeSended) || fileSize.equals(rangeSended.add(BigDecimal.ONE))){
			ApiListingResponse api = getMap().get(fileName);
			api.setStatus(StatusUploadFile.COMPLETED);
		}
	}
	/**
	 * This Method create or upadate register in Map
	 * @param requestFile
	 * @param start
	 * @param fileName
	 * @param file
	 */
	private void controlsInformationInHashMap(MultipartHttpServletRequest requestFile, long start, String fileName, File file) {
		ApiListingResponse apiListingResponse;
		//Map already contais the file, just update some informations
		if(getMap().containsKey(fileName)){
			apiListingResponse = getMap().get(fileName);
			//sum the time execution
			BigDecimal time = apiListingResponse.getSendingTime().add(Util.convertNanoToSeconds(start, System.nanoTime()));
			apiListingResponse.setChunksQuantity(apiListingResponse.getChunksQuantity()+1);
			apiListingResponse.setSendingTime(time);
			apiListingResponse.setFile(file);
			
		}else{
			//First requisition of this file, instantiate a new POJO
			apiListingResponse = createPojoApiListingResponse(requestFile, start, fileName, file);
			map.put(fileName, apiListingResponse);
		}
	}
	/**
	 * Create the POJO ApiListingResponse
	 * @param requestFile
	 * @param start: time of start the method execution
	 * @param fileName: name of file
	 * @param file
	 * @author pauloamorim
	 * @return ApiListingResponse
	 */
	private ApiListingResponse createPojoApiListingResponse(MultipartHttpServletRequest requestFile, long start, String fileName,
			File file) {
		ApiListingResponse apiListingResponse;
		apiListingResponse = new ApiListingResponse();
		apiListingResponse.setFileName(fileName);
		apiListingResponse.setStatus(StatusUploadFile.IN_PROGRESS);
		apiListingResponse.setIdentification(requestFile.getParameter("identification"));
		apiListingResponse.setChunksQuantity(1);
		apiListingResponse.setSendingTime(Util.convertNanoToSeconds(start, System.nanoTime()));
		apiListingResponse.setLinkDownloadFile(linkDownload.concat(fileName));
		apiListingResponse.setFile(file);
		return apiListingResponse;
	}
	
	/**
	 * The header is provided in format "bytes 0-999999/3245760"
	 * @param requestFile
	 * @author pauloamorim
	 * @return BigDecimal
	 */
	
	public BigDecimal getContenteRange(MultipartHttpServletRequest requestFile){
		String header = requestFile.getHeader("content-range");
		if(header == null)
			return BigDecimal.ZERO;
		return new BigDecimal(header.split("/")[1]);
	}
	/**
	 * This Method only want to get the limit os bytes send in post 
	 * The header is provided in format "bytes 0-999999/3245760"
	 * @param requestFile
	 * @author pauloamorim
	 * @return BigDecimal
	 */
	public BigDecimal getRangeSended(MultipartHttpServletRequest requestFile){
		String header = requestFile.getHeader("content-range");
		if(header == null)
			return BigDecimal.ZERO;
		
		String firstPartHeader = header.split("/")[0];
		return new BigDecimal(firstPartHeader.split("-")[1]);
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
	public Map<String, ApiListingResponse> getMap() {
		return map;
	}
	public void setMap(Map<String, ApiListingResponse> map) {
		this.map = map;
	}
	
}
