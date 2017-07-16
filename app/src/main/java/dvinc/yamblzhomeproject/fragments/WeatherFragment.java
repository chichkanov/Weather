package dvinc.yamblzhomeproject.fragments;
/*
 * Created by DV on Space 5 
 * 13.07.2017
 */

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

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.RetrofitJob;
import dvinc.yamblzhomeproject.model.WeatherResponse;

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
    public static final String SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather,
                container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getContext() != null) updateData();
    }

    @OnClick(R.id.getDataButton)
    public void getData() {
        // TODO: Для юи применяются прошлые данные, это плохо, надо пофиксить
        new RetrofitJob().run(getContext());
        updateData();
    }

    public void updateData(){
        SharedPreferences str = getActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String string = str.getString("JSON", "");
        long lastUpdateTime = str.getLong("LAST UPDATE TIME", 0L);
        Gson jsonObject = new Gson();
        WeatherResponse weatherResponse = jsonObject.fromJson(string, WeatherResponse.class);

        Log.v("JSON LOAD FROM SHARED", string);

        if (weatherResponse != null) {
            int temp = (int) (weatherResponse.getMain().getTemp() - 273);
            int tempMax = (int) (weatherResponse.getMain().getTempMax() - 273);
            int tempMin = (int) (weatherResponse.getMain().getTempMin() - 273);
            int pressure = weatherResponse.getMain().getPressure();
            int humidity = weatherResponse.getMain().getHumidity();
            float visibility = weatherResponse.getVisibility() / 1000;
            float wind = weatherResponse.getWind().getSpeed();

            String temperature = temp + getResources().getString(R.string.temperature_celsius);
            String temperatureMax = tempMax + getResources().getString(R.string.temperature_celsius);
            String temperatureMin = tempMin + getResources().getString(R.string.temperature_celsius);
            String pressureString = pressure + getResources().getString(R.string.pressure_unit);
            String humidityString = humidity + getResources().getString(R.string.humidity_unit);
            String visibilityString = visibility + getResources().getString(R.string.visibility_unit);
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
}
