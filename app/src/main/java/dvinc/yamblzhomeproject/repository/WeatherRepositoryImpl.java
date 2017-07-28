package dvinc.yamblzhomeproject.repository;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.net.WeatherApi;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;
import dvinc.yamblzhomeproject.utils.Settings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String API_KEY = "21cd7fe880c848c7d533498d2413f293";
    private static final String SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME";
    private static final String JSON = "JSON";
    private static final String LAST_UPDATE_TIME = "LAST UPDATE TIME";

    @Inject
    Settings settings;

    public WeatherRepositoryImpl(){
        App.getComponent().inject(this);
    }

    @Override
    public WeatherResponse getDataFromCache(Context context) {

        SharedPreferences str = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String string = str.getString(JSON, "");
        Gson jsonObject = new Gson();

        return jsonObject.fromJson(string, WeatherResponse.class);
    }

    @Override
    public void getDataFromWeb(final Context context, final CallbackWeather callbackWeather) {
        WeatherApi api = App.get(context).getApi();
        Call<WeatherResponse> weatherResponseCall = api.getTranslate(settings.getCurrentCityLocationLat(), settings.getCurrentCityLocationLong(), API_KEY);
        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                WeatherResponse weatherResponse = response.body();
                Gson jsonObject = new Gson();
                String callbackStringFromJSON = jsonObject.toJson(weatherResponse);

                SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE).edit();
                editor.putString(JSON, callbackStringFromJSON);
                long currentTimeMillis = System.currentTimeMillis();
                editor.putLong(LAST_UPDATE_TIME, currentTimeMillis);
                editor.apply();
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
    public void updateWeatherData(final Context context) {
        WeatherApi api = App.get(context).getApi();
        Call<WeatherResponse> weatherResponseCall = api.getTranslate(settings.getCurrentCityLocationLat(), settings.getCurrentCityLocationLong(), API_KEY);
        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                WeatherResponse weatherResponse = response.body();
                Gson jsonObject = new Gson();
                String callbackStringFromJSON = jsonObject.toJson(weatherResponse);

                SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE).edit();
                editor.putString(JSON, callbackStringFromJSON);
                long currentTimeMillis = System.currentTimeMillis();
                editor.putLong(LAST_UPDATE_TIME, currentTimeMillis);
                editor.apply();
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.v("Retrofit", "Retrofit failure");
            }
        });
    }
}
