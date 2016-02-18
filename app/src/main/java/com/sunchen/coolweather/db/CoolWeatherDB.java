package com.sunchen.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunchen.coolweather.model.City;
import com.sunchen.coolweather.model.Country;
import com.sunchen.coolweather.model.Province;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunchen on 2016/2/17.
 */
public class CoolWeatherDB {
    /*
    * 数据库名
    * */
    public static final String DB_NAME="cool_weather";
    /*
    * 数据库版本号
    * */
    public static final int VERSION=1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    /*
    * 将构造方法私有化
    * */
    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbhelper=new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
        db=dbhelper.getWritableDatabase();
    }

    /*
    * 获取CoolWeatherDB的单一实例
    * */
    public synchronized static CoolWeatherDB getInstance(Context context){
        if(coolWeatherDB==null){
            coolWeatherDB=new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    /*
    * 将province实例存储到数据库
    * */
    public void save(Province province){
        if(province!=null){
            ContentValues contentValues=new ContentValues();
            contentValues.put("province_name",province.getProvinceName());
            contentValues.put("province_code",province.getProvinceCode());
            db.insert("Province",null,contentValues);
        }
    }

    /*
    * 从数据库读取全国所有的省份信息
    * */
    public List<Province> loadprovinces(){
        List<Province> list=new ArrayList<Province>();
        Cursor cursor=db.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Province province=new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while(cursor.moveToNext());
        }
        if(cursor!=null) {
            cursor.close();
        }
        return list;
    }

    /*
    * 添加city实例
    * */
    public void saveCity(City city){
        if(city!=null){
            ContentValues values=new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City",null,values);
        }
    }
    /*
    * 从数据库读取所有的城市信息
    * */
    public List<City> loadCities(int provinceid){
        List<City> lsit=new ArrayList<City>();
        Cursor cursor=db.query("City",null,"province_id = ?",new String[]{String.valueOf(provinceid)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                City city=new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceid);
                lsit.add(city);
            }while (cursor.moveToNext());
        }
        if(cursor!=null){
            cursor.close();
        }
        return lsit;
    }

    /*
    * 存储country实例到数据库
    * */
    public void saveCountry(Country country){
        if(country!=null){
            ContentValues values=new ContentValues();
            values.put("country_name",country.getCountryName());
            values.put("country_code",country.getGetCountryCode());
            values.put("city_id",country.getCityId());
            db.insert("Country",null,values);
        }
    }

    public List<Country> loadCountyies(int cityId){
        List<Country>list=new ArrayList<Country>();
        Cursor cursor=db.query("Country",null,"city_id=?",new String[]{String.valueOf(cityId)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                Country country=new Country();
                country.setId(cursor.getInt(cursor.getColumnIndex("id")));
                country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
                country.setGetCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
                country.setCityId(cityId);
                list.add(country);
            }while (cursor.moveToNext());
        }
        if(cursor!=null){
            cursor.close();
        }
        return list;
    }



}
