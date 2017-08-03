package dvinc.yamblzhomeproject.repository.model.weather;

import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.repository.model.weather.hourForecast.WeatherForecastDailyResponse;

public class WeatherCombiner {

    private WeatherResponse weatherResponse;
    private WeatherForecastDailyResponse weatherForecastDailyResponse;

    public WeatherCombiner(WeatherResponse weatherResponse, WeatherForecastDailyResponse weatherForecastDailyResponse) {
        this.weatherResponse = weatherResponse;
        this.weatherForecastDailyResponse = weatherForecastDailyResponse;
    }

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    public WeatherForecastDailyResponse getWeatherForecastDailyResponse() {
        return weatherForecastDailyResponse;
    }

    public void setWeatherForecastDailyResponse(WeatherForecastDailyResponse weatherForecastDailyResponse) {
        this.weatherForecastDailyResponse = weatherForecastDailyResponse;
    }
}
