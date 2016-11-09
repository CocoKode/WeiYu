package com.example.ldy.weiyuweather.Decode;

import com.example.ldy.weiyuweather.Gson.Weather;
import com.example.ldy.weiyuweather.Gson.WholeWeather;
import com.example.ldy.weiyuweather.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by LDY on 2016/11/7.
 * 废弃
 */
final class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    /*
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    */

    DecodeResponseBodyConverter() {
        //this.gson = gson;
        //this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            String result = convert2GB.decodeUnicode(response);
            return (T) result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
