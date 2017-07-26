package dvinc.yamblzhomeproject.net;
/*
 * Created by DV on Space 5 
 * 14.07.2017
 */

import dvinc.yamblzhomeproject.repository.model.predictions.CityPrediction;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApi {
    @GET("data/2.5/weather")
    Call<WeatherResponse> getTranslate(@Query("q") String city, @Query("appid") String appid);

    @GET("https://maps.googleapis.com/maps/api/place/autocomplete/json")
    Call<CityPrediction> getPrediction(@Query("key") String key, @Query("types") String filterType, @Query("input") String input);
}
