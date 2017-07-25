package com.pauloamorim.apifile.pojo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pauloamorim.apifile.enums.StatusUploadFile;

public class ApiListingResponse {
	
	private String identification;
	private String fileName;
	private StatusUploadFile status;
	private double sendingTime;
	private Integer chunksQuantity;
	private String linkDownloadFile;
	private File file;
	
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
	public double getSendingTime() {
		return sendingTime;
	}
	public void setSendingTime(double sendingTime) {
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
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
}


