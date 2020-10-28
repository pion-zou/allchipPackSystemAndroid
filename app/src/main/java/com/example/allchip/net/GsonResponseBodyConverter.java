package com.example.allchip.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by lijing on 2017/8/30.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            T o = adapter.read(jsonReader);
            if(o instanceof TModel){
                TModel tm = (TModel) o;
                if(tm.getStatus() == TModel.TOKEN_INVALID ){
                }
            }
            return o;
        } finally {
            value.close();
        }
    }
}
