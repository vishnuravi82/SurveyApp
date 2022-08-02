package com.labournet.surveyapp.model;

public class WaterSource {
    private String HouseId;
    private String SourceId;
    private String Irrigation;
    private String age;
    private String season;
    private String water_availability;


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getWater_availability() {
        return water_availability;
    }

    public void setWater_availability(String water_availability) {
        this.water_availability = water_availability;
    }



    public String getIrrigation() {
        return Irrigation;
    }

    public void setIrrigation(String irrigation) {
        Irrigation = irrigation;
    }

    public String getHouseId() {
        return HouseId;
    }

    public void setHouseId(String houseId) {
        HouseId = houseId;
    }

    public String getSourceId() {
        return SourceId;
    }

    public void setSourceId(String sourceId) {
        SourceId = sourceId;
    }
}
