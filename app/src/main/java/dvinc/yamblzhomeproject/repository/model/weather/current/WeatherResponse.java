package dvinc.yamblzhomeproject.repository.model.weather.current;
/*
 * Created by DV on Space 5 
 * 14.07.2017
 */

import android.arch.persistence.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import dvinc.yamblzhomeproject.repository.model.weather.core.Clouds;
import dvinc.yamblzhomeproject.repository.model.weather.core.Coord;
import dvinc.yamblzhomeproject.repository.model.weather.core.Main;
import dvinc.yamblzhomeproject.repository.model.weather.core.Weather;
import dvinc.yamblzhomeproject.repository.model.weather.core.Wind;

public class WeatherResponse {

    @Embedded
    @SerializedName("coord")
    @Expose
    private Coord coordCurrent;
    @SerializedName("weather")
    @Expose
    private List<Weather> weatherCurrent = null;
    @Embedded
    @SerializedName("main")
    @Expose
    private Main mainCurrent;
    @SerializedName("visibility")
    @Expose
    private double visibilityCurrent;
    @Embedded
    @SerializedName("wind")
    @Expose
    private Wind windCurrent;
    @Embedded
    @SerializedName("clouds")
    @Expose
    private Clouds cloudsCurrent;
    @Embedded
    @SerializedName("sys")
    @Expose
    private Sys sysCurrent;

    public Coord getCoordCurrent() {
        return coordCurrent;
    }

    public void setCoordCurrent(Coord coordCurrent) {
        this.coordCurrent = coordCurrent;
    }

    public List<Weather> getWeatherCurrent() {
        return weatherCurrent;
    }

    public void setWeatherCurrent(List<Weather> weatherCurrent) {
        this.weatherCurrent = weatherCurrent;
    }

    public Main getMainCurrent() {
        return mainCurrent;
    }

    public void setMainCurrent(Main mainCurrent) {
        this.mainCurrent = mainCurrent;
    }

    public double getVisibilityCurrent() {
        return visibilityCurrent;
    }

    public void setVisibilityCurrent(double visibilityCurrent) {
        this.visibilityCurrent = visibilityCurrent;
    }

    public Wind getWindCurrent() {
        return windCurrent;
    }

    public void setWindCurrent(Wind windCurrent) {
        this.windCurrent = windCurrent;
    }

    public Clouds getCloudsCurrent() {
        return cloudsCurrent;
    }

    public void setCloudsCurrent(Clouds cloudsCurrent) {
        this.cloudsCurrent = cloudsCurrent;
    }

    public Sys getSysCurrent() {
        return sysCurrent;
    }

    public void setSysCurrent(Sys sysCurrent) {
        this.sysCurrent = sysCurrent;
    }

    public WeatherResponse() {
    }
}