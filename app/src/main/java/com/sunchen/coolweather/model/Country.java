package com.sunchen.coolweather.model;

/**
 * Created by sunchen on 2016/2/17.
 */
public class Country {
    private int id;
    private String countryName;
    private String getCountryCode;
    private int cityId;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getGetCountryCode() {
        return getCountryCode;
    }

    public void setGetCountryCode(String getCountryCode) {
        this.getCountryCode = getCountryCode;
    }
}
