package dvinc.yamblzhomeproject.ui.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.data.uiModel.CurrentWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.DailyWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.HourlyWeatherUi;
import dvinc.yamblzhomeproject.utils.WeatherUtils;

public class WeatherFragment extends MvpAppCompatFragment implements WeatherView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TITLE_KEY = "titleKey";

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
    @BindView(R.id.tv_weather_last_update)
    RelativeTimeTextView tvLastUpdate;

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

    public static WeatherFragment newInstance(String cityName) {
        WeatherFragment weatherFragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_KEY, cityName);
        weatherFragment.setArguments(bundle);
        return weatherFragment;
    }

    @ProvidePresenter
    WeatherPresenter providePresenter() {
        return App.getComponent().getWeatherPresenter();
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

        weatherPresenter.getWeather();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    public void updateWeatherCurrent(CurrentWeatherUi currentWeather) {
        tvTemperature.setText(getString(R.string.weather_temp_cels, currentWeather.getTemp()));
        tvDesc.setText(currentWeather.getDescription());
        tvHumidity.setText(getString(R.string.weather_humidity, currentWeather.getHumidity()));
        tvWindSpeed.setText(getString(R.string.weather_wind_speed_metr, currentWeather.getWindSpeed()));
        tvPressure.setText(getString(R.string.weather_pressure_hpa, currentWeather.getPressure()));
        tvMaxMinTemp.setText(getString(R.string.weather_temperature_minmax, currentWeather.getTempMax(), currentWeather.getTempMin()));
        weatherIcon.setIconResource(getString(WeatherUtils.getMainIcon(currentWeather.getIcon())));
    }

    @Override
    public void updateWeatherHourly(List<HourlyWeatherUi> hourWeather) {
        adapterHourly.setDataset(hourWeather);
    }

    @Override
    public void updateWeatherDaily(List<DailyWeatherUi> dayWeather) {
        adapterDaily.setDataset(dayWeather);
    }

    @Override
    public void updateLastUpdateTime(long date) {
        tvLastUpdate.setReferenceTime(date);
        tvLastUpdate.setPrefix(getString(R.string.weather_update_time) + " ");
    }

    @Override
    public void showCityName() {
        getActivity().setTitle(getArguments().getString(TITLE_KEY, getString(R.string.nav_head_weather)));
    }

    @Override
    public void showError() {
        Log.e("WeatherFragment", "Error Shown");
        Toast.makeText(getActivity(), getResources().getString(R.string.network_error_message), Toast.LENGTH_SHORT).show();
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
