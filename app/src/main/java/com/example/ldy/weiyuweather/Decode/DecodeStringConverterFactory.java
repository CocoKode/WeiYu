package com.example.ldy.weiyuweather.Decode;

import com.example.ldy.weiyuweather.Gson.WholeWeather;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by LDY on 2016/11/7.
 *
 * 建立自己的converter处理返回数据
 * https://github.com/brokge/Retrofit2.0-JSONCoverter
 * https://github.com/zhimin115200/Retrofit_StringAndJsonConverter
 * http://caiyao.name/2016/01/13/Retrofit%E4%BD%BF%E7%94%A8%E4%B9%8B%E8%87%AA%E5%AE%9A%E4%B9%89Converter/
 */
public final class DecodeStringConverterFactory extends Converter.Factory {

    public static DecodeStringConverterFactory create() {
        return new DecodeStringConverterFactory();
    }

    /*
    public static DecodeStringConverterFactory create(Gson gson) {
        return new DecodeStringConverterFactory(gson);
    }

    private final Gson gson;

    private DecodeStringConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }
    */

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        //TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new DecodeResponseBodyConverter<String>();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        //TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new DecodeRequestBodyConverter<String>();
    }
}
