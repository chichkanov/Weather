package dvinc.yamblzhomeproject.repository;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.util.Log;

import com.google.gson.Gson;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.net.WeatherApi;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;
import dvinc.yamblzhomeproject.utils.Settings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String API_KEY = "21cd7fe880c848c7d533498d2413f293";

    @Inject
    Settings settings;
    @Inject
    WeatherApi weatherApi;

    @Inject
    public WeatherRepositoryImpl(){
        
    }

    @Override
    public WeatherResponse getDataFromCache() {
        return settings.getWeather();
    }

    @Override
    public void getDataFromWeb(final CallbackWeather callbackWeather) {
        Call<WeatherResponse> weatherResponseCall = weatherApi.getTranslate(settings.getCurrentCityLocationLat(), settings.getCurrentCityLocationLong(), API_KEY);
        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                WeatherResponse weatherResponse = response.body();
                Gson jsonObject = new Gson();
                String callbackStringFromJSON = jsonObject.toJson(weatherResponse);
                settings.saveWeather(callbackStringFromJSON);
                callbackWeather.onSuccess(weatherResponse);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.v("Retrofit", t.getMessage());
                callbackWeather.onError();
            }
        });
    }

    @Override
    public void updateWeatherData() {
        Call<WeatherResponse> weatherResponseCall = weatherApi.getTranslate(settings.getCurrentCityLocationLat(), settings.getCurrentCityLocationLong(), API_KEY);
        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                WeatherResponse weatherResponse = response.body();
                Gson jsonObject = new Gson();
                String callbackStringFromJSON = jsonObject.toJson(weatherResponse);
                settings.saveWeather(callbackStringFromJSON);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.v("Retrofit", "Retrofit failure");
            }
        });
    }
}
