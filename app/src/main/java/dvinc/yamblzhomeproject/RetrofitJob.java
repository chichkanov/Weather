package dvinc.yamblzhomeproject;
/*
 * Created by DV on Space 5 
 * 15.07.2017
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import dvinc.yamblzhomeproject.model.WeatherResponse;
import dvinc.yamblzhomeproject.net.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class RetrofitJob {
    private static final String API_KEY = "21cd7fe880c848c7d533498d2413f293";
    private static final String CITY = "Moscow";
    private static final String SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME";

    public void run(final Context context){
        RetrofitApi api = App.get(context).getApi();
        Call<WeatherResponse> weatherResponseCall = api.getTranslate(CITY, API_KEY);
        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                WeatherResponse weatherResponse = response.body();
                Gson jsonObject = new Gson();
                String callbackStringFromJSON = jsonObject.toJson(weatherResponse);

                SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE).edit();
                editor.putString("JSON", callbackStringFromJSON);
                //Log.v("Retrofit", callbackStringFromJSON);
                long currentTimeMillis = System.currentTimeMillis();
                editor.putLong("LAST UPDATE TIME", currentTimeMillis);
                editor.apply();
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.v("Retrofit", "Retrofit failure");
            }
        });
    }
}
