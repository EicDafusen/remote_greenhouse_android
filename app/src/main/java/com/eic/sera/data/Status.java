package com.eic.sera.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Status implements Serializable {

    @SerializedName("temp") // find "temp" value in JSON data and match with String Temperature
    private String Temperature;
    @SerializedName("humid")
    private String Humidity;


    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public Status(String temperature, String humidity) {
        Temperature = temperature;
        Humidity = humidity;
    }
}
