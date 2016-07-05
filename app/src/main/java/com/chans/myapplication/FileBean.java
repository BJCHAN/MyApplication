package com.chans.myapplication;

/**
 * description :
 * Created by Chans Renhenet
 * 2015/9/10
 */
public class FileBean {

	private String fileName;
	private String filePath;
	private String createTime;
	private int type;

	public FileBean() {
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
