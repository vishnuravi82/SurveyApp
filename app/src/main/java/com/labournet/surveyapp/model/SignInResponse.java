package com.labournet.surveyapp.model;

import com.google.gson.annotations.SerializedName;

public class SignInResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("description")
    private String description;
    @SerializedName("app_status")
    private boolean app_status;
    @SerializedName("user_id")
    private String  user_id;


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

    public boolean isApp_status() {
        return app_status;
    }

    public void setApp_status(boolean app_status) {
        this.app_status = app_status;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
