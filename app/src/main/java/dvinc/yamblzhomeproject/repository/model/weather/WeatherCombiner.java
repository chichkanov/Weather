package dvinc.yamblzhomeproject.repository.model.weather;

import android.arch.persistence.room.Embedded;

import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.repository.model.weather.dailyForecast.WeatherForecastDailyResponse;
import dvinc.yamblzhomeproject.repository.model.weather.hourForecast.WeatherForecastHourlyResponse;

public class WeatherCombiner {

    @Embedded
    private WeatherResponse weatherResponse;
    @Embedded
    private WeatherForecastHourlyResponse weatherForecastHourlyResponse;
    @Embedded
    private WeatherForecastDailyResponse weatherForecastDailyResponse;

    public WeatherCombiner(WeatherResponse weatherResponse, WeatherForecastHourlyResponse weatherForecastHourlyResponse, WeatherForecastDailyResponse weatherForecastDailyResponse) {
        this.weatherResponse = weatherResponse;
        this.weatherForecastHourlyResponse = weatherForecastHourlyResponse;
        this.weatherForecastDailyResponse = weatherForecastDailyResponse;
    }

    public WeatherForecastDailyResponse getWeatherForecastDailyResponse() {
        return weatherForecastDailyResponse;
    }

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public WeatherForecastHourlyResponse getWeatherForecastHourlyResponse() {
        return weatherForecastHourlyResponse;
    }

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    public void setWeatherForecastHourlyResponse(WeatherForecastHourlyResponse weatherForecastHourlyResponse) {
        this.weatherForecastHourlyResponse = weatherForecastHourlyResponse;
    }

    public void setWeatherForecastDailyResponse(WeatherForecastDailyResponse weatherForecastDailyResponse) {
        this.weatherForecastDailyResponse = weatherForecastDailyResponse;
    }

}
