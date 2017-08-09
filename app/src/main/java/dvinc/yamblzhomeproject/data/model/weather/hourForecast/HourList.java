package dvinc.yamblzhomeproject.data.model.weather.hourForecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import dvinc.yamblzhomeproject.data.model.weather.core.Clouds;
import dvinc.yamblzhomeproject.data.model.weather.core.Main;
import dvinc.yamblzhomeproject.data.model.weather.core.Weather;
import dvinc.yamblzhomeproject.data.model.weather.core.Wind;

public class HourList {

    @SerializedName("clouds")
    @Expose
    private Clouds cloudsHourly;
    @SerializedName("dt")
    @Expose
    private long dtHourly;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxtHourly;
    @SerializedName("main")
    @Expose
    private Main mainHourly;
    @SerializedName("weather")
    @Expose
    private java.util.List<Weather> weatherHourly = null;
    @SerializedName("wind")
    @Expose
    private Wind windHourly;

    public Clouds getCloudsHourly() {
        return cloudsHourly;
    }

    public void setCloudsHourly(Clouds cloudsHourly) {
        this.cloudsHourly = cloudsHourly;
    }

    public long getDtHourly() {
        return dtHourly;
    }

    public void setDtHourly(long dtHourly) {
        this.dtHourly = dtHourly;
    }

    public String getDtTxtHourly() {
        return dtTxtHourly;
    }

    public void setDtTxtHourly(String dtTxtHourly) {
        this.dtTxtHourly = dtTxtHourly;
    }

    public Main getMainHourly() {
        return mainHourly;
    }

    public void setMainHourly(Main mainHourly) {
        this.mainHourly = mainHourly;
    }

    public List<Weather> getWeatherHourly() {
        return weatherHourly;
    }

    public void setWeatherHourly(List<Weather> weatherHourly) {
        this.weatherHourly = weatherHourly;
    }

    public Wind getWindHourly() {
        return windHourly;
    }

    public void setWindHourly(Wind windHourly) {
        this.windHourly = windHourly;
    }

    public HourList() {
    }
}
