package com.sunchen.coolweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunchen.coolweather.R;
import com.sunchen.coolweather.util.HttpCallbackListener;
import com.sunchen.coolweather.util.HttpUtil;
import com.sunchen.coolweather.util.LogUtil;
import com.sunchen.coolweather.util.Utility;

import java.lang.reflect.Array;

public class WeatherActivity extends Activity implements View.OnClickListener{

    private LinearLayout weatherInfolayout;
    private TextView cityNameText;
    private TextView publishText;
    private TextView weatherDespText;
    private TextView temp1Text;
    private TextView temp2Text;
    private TextView currentDateText;
    private Button switchCity;
    private Button refreshWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        switchCity=(Button) findViewById(R.id.switch_city);
        refreshWeather=(Button) findViewById(R.id.refresh_weather);
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
        weatherInfolayout=(LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText=(TextView) findViewById(R.id.city_name);
        publishText=(TextView) findViewById(R.id.publish_text);
        weatherDespText=(TextView) findViewById(R.id.weather_desp);
        temp1Text=(TextView) findViewById(R.id.temp1);
        temp2Text=(TextView) findViewById(R.id.temp2);
        currentDateText=(TextView) findViewById(R.id.current_date);
        String countryCode=getIntent().getStringExtra("country_code");
        if(!TextUtils.isEmpty(countryCode)){
            publishText.setText("同步中...");
            weatherInfolayout.setVisibility(View.VISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeatherCode(countryCode);
        }else{
            showWeather();
        }
    }

    private void queryWeatherCode(String countrycode){
        String address="http://www.weather.com.cn/data/list3/city"+countrycode+".xml";
        LogUtil.d("address:",address);
        queryFromServer(address,"countryCode");
    }

    private void queryWeatherInfo(String weatherCode){
        String address="http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
        queryFromServer(address,"weatherCode");
    }


    private void queryFromServer(final String address,final String type){
        HttpUtil.sendhttpRequest(address,new HttpCallbackListener() {
            @Override
            public void OnFinsh(String response) {
                if("countryCode".equals(type)){
                    if(!TextUtils.isEmpty(response)){
                        String [] array=response.split("\\|");
                        LogUtil.d("response:",response);
                        if(array!=null&&array.length==2){
                            LogUtil.d("arraysize:",String.valueOf(array.length));
                            String weathercode=array[1];
                            queryWeatherInfo(weathercode);
                        }
                    }
                }else if ("weatherCode".equals(type)){
                    LogUtil.d("weatherCode:","here");
                    Utility.handleWeatherResponse(WeatherActivity.this,response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.d("UIThread:","here");
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void OnError(Exception e) {
                publishText.setText("同步失败!");
            }
        });
    }


    private void showWeather(){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("city_name",""));
        temp1Text.setText(prefs.getString("temp1",""));
        temp2Text.setText(prefs.getString("temp2",""));
        weatherDespText.setText(prefs.getString("weather_desp",""));
        publishText.setText("今天"+prefs.getString("publish_time","")+"发布");
        currentDateText.setText(prefs.getString("current_date",""));
        weatherInfolayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.switch_city:
                Intent intent=new Intent(this,choose_areaActivity.class);
                intent.putExtra("from_weather_activity",true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                publishText.setText("同步中...");
                SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(this);
                String weathreCode=pref.getString("weather_code","");
                if(!TextUtils.isEmpty(weathreCode)){
                    queryWeatherInfo(weathreCode);
                }
                break;
            default:
                break;
        }
    }
}
