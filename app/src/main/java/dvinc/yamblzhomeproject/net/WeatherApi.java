package dvinc.yamblzhomeproject.net;
/*
 * Created by DV on Space 5 
 * 14.07.2017
 */

import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("weather")
    Observable<WeatherResponse> getWeather(@Query("lat") String cityLat, @Query("lon") String cityLong, @Query("appid") String appid);
}
