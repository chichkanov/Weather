package dvinc.yamblzhomeproject.data.model.weather.hourForecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherForecastHourlyResponse {

    @SerializedName("list")
    @Expose
    private List<HourList> list = null;

    public List<HourList> getList() {
        return list;
    }

    public void setList(List<HourList> list) {
        this.list = list;
    }

    public WeatherForecastHourlyResponse() {
    }
}