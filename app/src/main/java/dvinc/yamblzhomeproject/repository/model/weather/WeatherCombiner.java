package dvinc.yamblzhomeproject.repository.model.weather;

import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.repository.model.weather.dailyForecast.WeatherForecastDailyResponse;
import dvinc.yamblzhomeproject.repository.model.weather.hourForecast.WeatherForecastHourlyResponse;

public class WeatherCombiner {

    private WeatherResponse weatherResponse;
    private WeatherForecastHourlyResponse weatherForecastHourlyResponse;

    public WeatherForecastDailyResponse getWeatherForecastDailyResponse() {
        return weatherForecastDailyResponse;
    }

    private WeatherForecastDailyResponse weatherForecastDailyResponse;

    public WeatherCombiner(WeatherResponse weatherResponse, WeatherForecastHourlyResponse weatherForecastHourlyResponse, WeatherForecastDailyResponse weatherForecastDailyResponse) {
        this.weatherResponse = weatherResponse;
        this.weatherForecastHourlyResponse = weatherForecastHourlyResponse;
        this.weatherForecastDailyResponse = weatherForecastDailyResponse;
    }

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public WeatherForecastHourlyResponse getWeatherForecastHourlyResponse() {
        return weatherForecastHourlyResponse;
    }
}
