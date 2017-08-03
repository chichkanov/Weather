package dvinc.yamblzhomeproject.repository.model.weather.dailyForecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import dvinc.yamblzhomeproject.repository.model.weather.hourForecast.City;

public class WeatherForecastDailyResponse {

    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("cnt")
    @Expose
    private int cnt;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("list")
    @Expose
    private java.util.List<DailyList> list = null;
    @SerializedName("message")
    @Expose
    private double message;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public java.util.List<DailyList> getList() {
        return list;
    }

    public void setList(java.util.List<DailyList> list) {
        this.list = list;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }
}
