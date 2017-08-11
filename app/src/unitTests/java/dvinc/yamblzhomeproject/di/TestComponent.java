package dvinc.yamblzhomeproject.di;

import dvinc.yamblzhomeproject.net.background.AutoUpdateJob;
import dvinc.yamblzhomeproject.ui.main.MainPresenter;
import dvinc.yamblzhomeproject.ui.selectCity.SelectCityPresenter;
import dvinc.yamblzhomeproject.ui.weather.WeatherPresenter;

public class TestComponent implements AppComponent {

    @Override
    public void inject(AutoUpdateJob autoUpdateJob) {

    }

    @Override
    public MainPresenter getMainPresenter() {
        return null;
    }

    @Override
    public WeatherPresenter getWeatherPresenter() {
        return null;
    }

    @Override
    public SelectCityPresenter getSelectCityPresenter() {
        return null;
    }
}
