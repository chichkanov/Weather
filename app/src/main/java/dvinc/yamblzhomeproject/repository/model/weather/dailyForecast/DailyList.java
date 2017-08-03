package dvinc.yamblzhomeproject.repository.model.weather.dailyForecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import dvinc.yamblzhomeproject.repository.model.weather.core.Weather;

public class DailyList {

    @SerializedName("clouds")
    @Expose
    private int clouds;
    @SerializedName("deg")
    @Expose
    private int deg;
    @SerializedName("dt")
    @Expose
    private int dt;
    @SerializedName("humidity")
    @Expose
    private int humidity;
    @SerializedName("pressure")
    @Expose
    private double pressure;
    @SerializedName("speed")
    @Expose
    private double speed;
    @SerializedName("temp")
    @Expose
    private Temp temp;
    @SerializedName("weather")
    @Expose
    private java.util.List<Weather> weather = null;
    @SerializedName("rain")
    @Expose
    private double rain;

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }
}
