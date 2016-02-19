package com.sunchen.coolweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.sunchen.coolweather.db.CoolWeatherDB;
import com.sunchen.coolweather.model.City;
import com.sunchen.coolweather.model.Country;
import com.sunchen.coolweather.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    /*
    * 解析服务器返回的JSON数据，并将解析出的数据存储到本地
    * */
    public static void handleWeatherResponse(Context context,String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            JSONObject weatherInfo=jsonObject.getJSONObject("weatherinfo");
            String cityName=weatherInfo.getString("city");
            String weatherCode=weatherInfo.getString("cityid");
            String temp1=weatherInfo.getString("temp1");
            String temp2=weatherInfo.getString("temp2");
            String weatherDesp=weatherInfo.getString("weather");
            String publishtime=weatherInfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishtime);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void saveWeatherInfo(Context context,String cityName,String weatherCode,String temp1,String temp2,String weatherDesp,String publisTime){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name",cityName);
        editor.putString("weather_code",weatherCode);
        editor.putString("temp1",temp1);
        editor.putString("temp2",temp2);
        editor.putString("weather_desp",weatherDesp);
        editor.putString("publish_time",publisTime);
        editor.putString("current_date",sdf.format(new Date()));
        editor.commit();
    }




}
