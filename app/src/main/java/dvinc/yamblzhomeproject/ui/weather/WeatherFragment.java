package dvinc.yamblzhomeproject.ui.weather;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.repository.model.weather.dailyForecast.WeatherForecastDailyResponse;
import dvinc.yamblzhomeproject.repository.model.weather.hourForecast.WeatherForecastHourlyResponse;
import dvinc.yamblzhomeproject.utils.WeatherUtils;

public class WeatherFragment extends MvpAppCompatFragment implements WeatherView, SwipeRefreshLayout.OnRefreshListener {

    private static String CITY_NAME_KEY = "cityNameKey";

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

    @BindView(R.id.weather_icon)
    WeatherIconView weatherIcon;

    @BindView(R.id.rv_weather_today)
    RecyclerView rvForecastHourly;
    @BindView(R.id.rv_weather_forecast)
    RecyclerView rvForecastDaily;

    private ForecastHourlyAdapter adapterHourly;
    private ForecastDailyAdapter adapterDaily;

    @InjectPresenter
    public WeatherPresenter weatherPresenter;

    private Unbinder unbinder;

    public static WeatherFragment newInstanse(String cityName) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(CITY_NAME_KEY, cityName);
        fragment.setArguments(args);
        return fragment;
    }

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
        initRecyclerViewHourly();
        initRecyclerViewDaily();
        setTitle();
        if (savedInstanceState == null) {
            weatherPresenter.getWeather();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setTitle() {
        String cityTitle = getArguments().getString(CITY_NAME_KEY);
        if (cityTitle != null) {
            getActivity().setTitle(cityTitle);
        }
    }

    private void initRecyclerViewHourly() {
        adapterHourly = new ForecastHourlyAdapter(new ArrayList<>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        rvForecastHourly.setLayoutManager(layoutManager);
        rvForecastHourly.addItemDecoration(decoration);
        rvForecastHourly.setAdapter(adapterHourly);
        rvForecastHourly.setNestedScrollingEnabled(false);
    }

    private void initRecyclerViewDaily() {
        adapterDaily = new ForecastDailyAdapter(new ArrayList<>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        rvForecastDaily.setLayoutManager(layoutManager);
        rvForecastDaily.addItemDecoration(decoration);
        rvForecastDaily.setAdapter(adapterDaily);
        rvForecastDaily.setNestedScrollingEnabled(false);
    }

    @Override
    public void updateWeatherCurrent(WeatherResponse weatherData) {
        tvTemperature.setText(getString(R.string.weather_temp_cels, (int) weatherData.getMainCurrent().getTemp() - 273));
        tvDesc.setText(weatherData.getWeatherCurrent().get(0).getDescription());
        tvHumidity.setText(getString(R.string.weather_humidity, weatherData.getMainCurrent().getHumidity()));
        tvWindSpeed.setText(getString(R.string.weather_wind_speed_metr, (int) weatherData.getWindCurrent().getSpeed()));
        tvPressure.setText(getString(R.string.weather_pressure_hpa, (int) weatherData.getMainCurrent().getPressure()));
        tvMaxMinTemp.setText(getString(R.string.weather_temperature_minmax, (int) weatherData.getMainCurrent().getTempMax() - 273, (int) weatherData.getMainCurrent().getTempMin() - 273));

        weatherIcon.setIconResource(getString(WeatherUtils.getIcon(weatherData.getWeatherCurrent().get(0).getIcon())));
    }

    @Override
    public void updateWeatherHourly(WeatherForecastHourlyResponse weatherForecastHourlyResponse) {
        adapterHourly.setDataset(weatherForecastHourlyResponse.getList());
    }

    @Override
    public void updateWeatherDaily(WeatherForecastDailyResponse weatherForecastDailyResponse) {
        adapterDaily.setDataset(weatherForecastDailyResponse.getListDaily());
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
