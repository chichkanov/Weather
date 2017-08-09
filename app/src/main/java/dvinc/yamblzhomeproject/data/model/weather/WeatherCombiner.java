package dvinc.yamblzhomeproject.data.model.weather;

import android.arch.persistence.room.Embedded;

import java.util.List;

import dvinc.yamblzhomeproject.data.uiModel.CurrentWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.DailyWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.HourlyWeatherUi;

public class WeatherCombiner {

    @Embedded
    private CurrentWeatherUi currentWeather;
    private List<HourlyWeatherUi> hourWeather;
    private List<DailyWeatherUi> dayWeather;

    private long updatedTime;

    public WeatherCombiner(CurrentWeatherUi currentWeather, List<HourlyWeatherUi> hourWeather, List<DailyWeatherUi> dayWeather) {
        this.currentWeather = currentWeather;
        this.hourWeather = hourWeather;
        this.dayWeather = dayWeather;
    }

    public CurrentWeatherUi getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeatherUi currentWeather) {
        this.currentWeather = currentWeather;
    }

    public List<HourlyWeatherUi> getHourWeather() {
        return hourWeather;
    }

    public void setHourWeather(List<HourlyWeatherUi> hourWeather) {
        this.hourWeather = hourWeather;
    }

    public List<DailyWeatherUi> getDayWeather() {
        return dayWeather;
    }

    public void setDayWeather(List<DailyWeatherUi> dayWeather) {
        this.dayWeather = dayWeather;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }
}
