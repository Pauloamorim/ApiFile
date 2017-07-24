package com.pauloamorim.apifile.pojo;

import com.pauloamorim.apifile.enums.StatusUploadFile;

public class ApiListingResponse {
	
	private String identification;
	private String fileName;
	private StatusUploadFile status;
	private Long sendingTime;
	private Integer chunksQuantity;
	private String linkDownloadFile;
	
	//////////////////////////////////////////////////
	//////////////////	GETTERS AND SETTERS //////////
	//////////////////////////////////////////////////
	
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public StatusUploadFile getStatus() {
		return status;
	}
	public void setStatus(StatusUploadFile status) {
		this.status = status;
	}
	public Long getSendingTime() {
		return sendingTime;
	}
	public void setSendingTime(Long sendingTime) {
		this.sendingTime = sendingTime;
	}
	public Integer getChunksQuantity() {
		return chunksQuantity;
	}
	public void setChunksQuantity(Integer chunksQuantity) {
		this.chunksQuantity = chunksQuantity;
	}
	public String getLinkDownloadFile() {
		return linkDownloadFile;
	}
	public void setLinkDownloadFile(String linkDownloadFile) {
		this.linkDownloadFile = linkDownloadFile;
	}
	
}


