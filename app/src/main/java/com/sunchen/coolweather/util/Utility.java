package com.sunchen.coolweather.util;

import android.text.TextUtils;

import com.sunchen.coolweather.db.CoolWeatherDB;
import com.sunchen.coolweather.model.City;
import com.sunchen.coolweather.model.Country;
import com.sunchen.coolweather.model.Province;

/**
 * Created by sunchen on 2016/2/18.
 */
public class Utility {
    /*
    * 解析处理服务器返回的省数据
    * */
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB,String response){
        if(!TextUtils.isEmpty(response)){
            String[] allprovinces=response.split(",");
            if(allprovinces!=null&&allprovinces.length>0){
                for(String p:allprovinces){
                    String []array=p.split("\\|");
                    Province province=new Province();
                    province.setProvinceName(array[1]);
                    province.setProvinceCode(array[0]);
                    coolWeatherDB.save(province);
                }
                return true;
            }
        }
        return false;
    }
    /*
    * 解析服务器返回的城市数据
    * */
    public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,String response,int provinceid){
        if(!TextUtils.isEmpty(response)){
            String [] allCities=response.split(",");
            if(allCities!=null&&allCities.length>0){
                for(String c:allCities){
                    String [] array=c.split("\\|");
                    City city=new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceid);
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /*
    * 解析服务器返回的县数据
    * */
    public synchronized static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,String response,int cityid){
        if(!TextUtils.isEmpty(response)){
            String[] allCounties=response.split(",");
            if(allCounties!=null&&allCounties.length>0){
                for(String c:allCounties){
                    String []array=c.split("\\|");
                    Country country=new Country();
                    country.setCityId(cityid);
                    country.setGetCountryCode(array[0]);
                    country.setCountryName(array[1]);
                    coolWeatherDB.saveCountry(country);
                }
                return true;
            }
        }
        return false;
    }


}
