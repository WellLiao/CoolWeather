package com.example.liaoqianwen.coolweather.util;

import android.text.TextUtils;
import android.view.TextureView;

import com.example.liaoqianwen.coolweather.db.CoolWeatherDB;
import com.example.liaoqianwen.coolweather.model.City;
import com.example.liaoqianwen.coolweather.model.Country;
import com.example.liaoqianwen.coolweather.model.Province;

import java.awt.font.TextAttribute;

/**
 * Created by liaoqianwen on 2016/7/25.
 */
public class Utility {
    /**解析和处理服务器返回的省级数据
     * @param coolWeatherDB
     * @param response
     * @return
     */
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response){
        if(!TextUtils.isEmpty(response)) {
            String[] allProvinces  = response.split(",");
            if( allProvinces !=null && allProvinces.length>0){
                for (String p : allProvinces){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    // 将解析出来的数据存储到Province表中
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, String response, int provinceId){
        if(!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if(allCities !=null && allCities.length>0){
                for(String c: allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    //
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean handleCountriesResponse(CoolWeatherDB coolWeatherDB, String response, int cityId){
        if(!TextUtils.isEmpty(response)) {
            String[] allCountries = response.split(",");
            if (allCountries != null && allCountries.length>0) {
                for (String c: allCountries) {
                    String[] array = c.split("\\|");
                    Country country = new Country();
                    country.setCountryCode(array[0]);
                    country.setCountryName(array[1]);
                    country.setCityId(cityId);
                    //
                    coolWeatherDB.saveCountry(country);
                }
                return true;
            }
        }
        return false;
    }
}
