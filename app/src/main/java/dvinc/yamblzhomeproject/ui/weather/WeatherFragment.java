package dvinc.yamblzhomeproject.ui.weather;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;

public class WeatherFragment extends MvpAppCompatFragment implements WeatherView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh_weather)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_weather_temperature)
    TextView tvTemperature;
    @BindView(R.id.tv_weather_humidity)
    TextView tvHumidity;
    @BindView(R.id.tv_weather_pressure)
    TextView tvPressure;
    @BindView(R.id.tv_weather_wind_speed)
    TextView tvWindSpeed;
    @BindView(R.id.tv_weather_desc)
    TextView tvDesc;
    @BindView(R.id.tv_weather_max_min)
    TextView tvMaxMinTemp;

    private String cityName = "";

    @InjectPresenter
    public WeatherPresenter weatherPresenter;

    private Unbinder unbinder;

    public WeatherFragment() {
        App.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather,
                container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (savedInstanceState == null) weatherPresenter.getWeather();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void updateWeatherParameters(WeatherResponse weatherData) {
        if (weatherData != null) {

            tvTemperature.setText(getString(R.string.weather_temp_cels, (int) weatherData.getMain().getTemp() - 273));
            tvDesc.setText(weatherData.getWeather().get(0).getDescription());
            tvHumidity.setText(getString(R.string.weather_humidity, weatherData.getMain().getHumidity()));
            tvWindSpeed.setText(getString(R.string.weather_wind_speed_metr, (int) weatherData.getWind().getSpeed()));
            tvPressure.setText(getString(R.string.weather_pressure_hpa, (int) weatherData.getMain().getPressure()));
            tvMaxMinTemp.setText(getString(R.string.weather_temperature_minmax, (int) weatherData.getMain().getTempMax() - 273, (int) weatherData.getMain().getTempMin() - 273));

            getActivity().setTitle(weatherData.getName());
        }
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), getResources().getString(R.string.network_error_message), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        weatherPresenter.getWeather();
    }
}
