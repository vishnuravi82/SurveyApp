package com.labournet.surveyapp.model;

import com.google.gson.annotations.SerializedName;

public class BasicFarmer {
    @SerializedName("success")
    private boolean success;
    @SerializedName("validation_error")
    private boolean validation_error;
    @SerializedName("description")
    private String description;
    @SerializedName("app_status")
    private boolean app_status;
    @SerializedName("house_id")
    private String house_hold_id;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isValidation_error() {
        return validation_error;
    }

    public void setValidation_error(boolean validation_error) {
        this.validation_error = validation_error;
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

    public String getHouse_hold_id() {
        return house_hold_id;
    }

    public void setHouse_hold_id(String house_hold_id) {
        this.house_hold_id = house_hold_id;
    }
}
