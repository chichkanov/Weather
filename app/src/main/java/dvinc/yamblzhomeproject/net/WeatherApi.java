package dvinc.yamblzhomeproject.net;
/*
 * Created by DV on Space 5 
 * 14.07.2017
 */

import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.repository.model.weather.dailyForecast.WeatherForecastDailyResponse;
import dvinc.yamblzhomeproject.repository.model.weather.hourForecast.WeatherForecastHourlyResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("weather")
    Observable<WeatherResponse> getCurrentWeather(@Query("lat") String cityLat,
                                                  @Query("lon") String cityLong,
                                                  @Query("appid") String appid,
                                                  @Query("lang") String language);

    @GET("forecast")
    Observable<WeatherForecastHourlyResponse> getHourForecast(@Query("lat") String cityLat,
                                                              @Query("lon") String cityLong,
                                                              @Query("appid") String appid,
                                                              @Query("lang") String language,
                                                              @Query("cnt") String hourCount);

    @GET("forecast/daily")
    Observable<WeatherForecastDailyResponse> getDailyForecast(@Query("lat") String cityLat,
                                                              @Query("lon") String cityLong,
                                                              @Query("appid") String appid,
                                                              @Query("lang") String language,
                                                              @Query("cnt") String hourCount);
}
