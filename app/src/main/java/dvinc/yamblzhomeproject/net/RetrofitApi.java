package dvinc.yamblzhomeproject.net;
/*
 * Created by DV on Space 5 
 * 14.07.2017
 */

import dvinc.yamblzhomeproject.repository.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApi {
    @GET("data/2.5/weather")
    Call<WeatherResponse> getTranslate(@Query("q") String city, @Query("appid") String appid);
}
