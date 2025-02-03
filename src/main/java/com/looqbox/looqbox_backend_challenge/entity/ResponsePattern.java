package com.looqbox.looqbox_backend_challenge.entity;

import java.util.HashMap;

import com.google.gson.Gson;

public class ResponsePattern<T> { //A generic class to give a response pattern for the project
    public ResponsePattern(T object) {
        this.result = object;
    }

    public static <T> ResponsePattern<T> getResponseObject(T object) {
        return new ResponsePattern<T>(object);
    }

    public static <T> T parseResult(Class<T> model, String object) {
        return new Gson().fromJson(new Gson().toJson(new Gson().fromJson(object, HashMap.class).get("result")), model);
    }

    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
