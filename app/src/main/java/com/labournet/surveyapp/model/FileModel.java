package com.labournet.surveyapp.model;

import java.io.Serializable;

import okhttp3.MultipartBody;


public class FileModel implements Serializable{

    private MultipartBody.Part filePart;
    private String fileType,fileName,filePath;

    public FileModel() {

    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public MultipartBody.Part getFilePart() {
        return filePart;
    }

    public void setFilePart(MultipartBody.Part filePart) {
        this.filePart = filePart;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
