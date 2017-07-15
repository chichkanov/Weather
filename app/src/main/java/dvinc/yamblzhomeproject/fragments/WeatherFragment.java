package dvinc.yamblzhomeproject.fragments;
/*
 * Created by DV on Space 5 
 * 13.07.2017
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.net.RetrofitApi;
import dvinc.yamblzhomeproject.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class WeatherFragment extends Fragment {

    @BindView(R.id.getDataButton) Button getDataButton;
    @BindView(R.id.temperatureTextView) TextView temperatureTextView;
    @BindView(R.id.tempMaxTextView) TextView tempMaxTextView;
    @BindView(R.id.tempMinTextView) TextView tempMinTextView;
    @BindView(R.id.pressureTextView) TextView pressureTextView;
    @BindView(R.id.humidityTextView) TextView humidityTextVIew;
    @BindView(R.id.lastUpdateWeatherTextView) TextView lastUpdateWeatherTextView;
    @BindView(R.id.visibilityTextView) TextView visibilityTextView;
    @BindView(R.id.windTextView) TextView windTextView;
    private static final String BASE_URL = "http://api.openweathermap.org/";
    private static final String API_KEY = "21cd7fe880c848c7d533498d2413f293";
    private static final String CITY = "Moscow";
    public static final String SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather,
                container, false);
        ButterKnife.bind(this, view);

        SharedPreferences str = getActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String string = str.getString("JSON", "");
        long lastUpdateTime = str.getLong("LAST UPDATE TIME", 0L);
        Gson jsonObject = new Gson();
        WeatherResponse weatherResponse = jsonObject.fromJson(string, WeatherResponse.class);

        if( weatherResponse != null & lastUpdateTime != 0) updateData(weatherResponse, lastUpdateTime);
        Log.v("JSON LOAD FROM SHARED", string);

        return view;
    }

    @OnClick(R.id.getDataButton)
    public void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi api = retrofit.create(RetrofitApi.class);
        Call<WeatherResponse> weatherResponseCall = api.getTranslate(CITY, API_KEY);

        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                WeatherResponse weatherResponse = response.body();

                Gson jsonObject = new Gson();
                String callbackStringFromJSON = jsonObject.toJson(weatherResponse);

                Context contex = getActivity();
                if (contex != null) {
                    SharedPreferences.Editor editor = contex.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE).edit();
                    editor.putString("JSON", callbackStringFromJSON);
                    Log.v("Retrofit", callbackStringFromJSON);
                    long currentTimeMillis = System.currentTimeMillis();
                    editor.putLong("LAST UPDATE TIME", currentTimeMillis);
                    editor.apply();
                    updateData(weatherResponse, currentTimeMillis);
                    Toast.makeText(getActivity(), getResources().getString(R.string.data_update), Toast.LENGTH_LONG).show();
                } else {
                    Log.v("Retrofit", "Load data crush");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.load_weather_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateData(WeatherResponse weatherResponse, long lastUpdateTime){
        int temp = (int) (weatherResponse.getMain().getTemp() - 273);
        int tempMax = (int) (weatherResponse.getMain().getTempMax() - 273);
        int tempMin = (int) (weatherResponse.getMain().getTempMin() - 273);
        int pressure = weatherResponse.getMain().getPressure();
        int humidity = weatherResponse.getMain().getHumidity();
        float visibility = weatherResponse.getVisibility()/1000;
        float wind = weatherResponse.getWind().getSpeed();

        String temperature = temp + getResources().getString(R.string.temperature_celsius);
        String temperatureMax = tempMax + getResources().getString(R.string.temperature_celsius);
        String temperatureMin = tempMin + getResources().getString(R.string.temperature_celsius);
        String pressureString = pressure + getResources().getString(R.string.pressure_unit);
        String humidityString = humidity + getResources().getString(R.string.humidity_unit);
        String visibilityString = visibility + getResources().getString(R.string.wind_speed_unit);
        String windString = wind + getResources().getString(R.string.wind_speed_unit);

        Date date = new Date(lastUpdateTime);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);

        temperatureTextView.setText(temperature);
        tempMaxTextView.setText(temperatureMax);
        tempMinTextView.setText(temperatureMin);
        pressureTextView.setText(pressureString);
        humidityTextVIew.setText(humidityString);
        lastUpdateWeatherTextView.setText(formattedDate);
        visibilityTextView.setText(visibilityString);
        windTextView.setText(windString);
    }
}
