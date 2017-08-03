package dvinc.yamblzhomeproject.repository.model.weather.hourForecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import dvinc.yamblzhomeproject.repository.model.weather.core.Clouds;
import dvinc.yamblzhomeproject.repository.model.weather.core.Main;
import dvinc.yamblzhomeproject.repository.model.weather.core.Weather;
import dvinc.yamblzhomeproject.repository.model.weather.core.Wind;

public class HourList {

    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("dt")
    @Expose
    private int dt;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("weather")
    @Expose
    private java.util.List<Weather> weather = null;
    @SerializedName("wind")
    @Expose
    private Wind wind;

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

}
