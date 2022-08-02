package com.labournet.surveyapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Athira on 24/3/20.
 */
public class PincodeDetailsResponse {
    @SerializedName("Status")
    private String status;
    @SerializedName("Message")
    private String message;
    @SerializedName("PostOffice")
    private ArrayList<PincodeModel> pincodeModels;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<PincodeModel> getPincodeModels() {
        return pincodeModels;
    }

    public void setPincodeModels(ArrayList<PincodeModel> pincodeModels) {
        this.pincodeModels = pincodeModels;
    }
}
