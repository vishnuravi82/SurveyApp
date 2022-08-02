package com.labournet.surveyapp.model;

import com.google.gson.annotations.SerializedName;


public class DefaultResponse {
    @SerializedName("app_status")
    private boolean appStatus;
    @SerializedName("success")
    private boolean success;
    @SerializedName("description")
    private String description;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;
    @SerializedName("PopupMessage")
    private DefaultResponse popUpMessage;

    public DefaultResponse getPopUpMessage() {
        return popUpMessage;
    }

    public void setPopUpMessage(DefaultResponse popUpMessage) {
        this.popUpMessage = popUpMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isAppStatus() {
        return appStatus;
    }

    public void setAppStatus(boolean appStatus) {
        this.appStatus = appStatus;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
