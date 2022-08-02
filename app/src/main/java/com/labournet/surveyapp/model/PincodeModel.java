package com.labournet.surveyapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Athira on 23/3/20.
 */
public class PincodeModel {
    @SerializedName("District")
    private String district;
    @SerializedName("State")
    private String state;
    @SerializedName("Country")
    private String country;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
