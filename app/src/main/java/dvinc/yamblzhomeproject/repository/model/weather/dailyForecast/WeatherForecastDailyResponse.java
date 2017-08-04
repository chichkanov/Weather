package dvinc.yamblzhomeproject.repository.model.weather.dailyForecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherForecastDailyResponse {

    @SerializedName("list")
    @Expose
    private java.util.List<DailyList> listDaily = null;

    public java.util.List<DailyList> getListDaily() {
        return listDaily;
    }

    public void setListDaily(java.util.List<DailyList> listDaily) {
        this.listDaily = listDaily;
    }

    public WeatherForecastDailyResponse() {
    }
}
