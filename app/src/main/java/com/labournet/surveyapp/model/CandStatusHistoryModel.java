package com.labournet.surveyapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Athira on 27/4/20.
 */
public class CandStatusHistoryModel {
    private int id;
    @SerializedName("lat")
    private String latitude;
    @SerializedName("lng")
    private String longitude;
    @SerializedName("ts")
    private String timestamp;
    @SerializedName("cid")
    private int candidateId;
    @SerializedName("sid")
    private int statusId;
    @SerializedName("reason")
    private String reason;
    @SerializedName("dateTime")
    private String dateTime;
    @SerializedName("remarks")
    private String remarks;
    private int uploadFlag;
    private String statusName;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(int uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}














