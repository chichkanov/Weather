package dvinc.yamblzhomeproject.di;

import dvinc.yamblzhomeproject.net.background.BGSyncJob;
import dvinc.yamblzhomeproject.ui.base.MainPresenter;
import dvinc.yamblzhomeproject.ui.selectCity.SelectCityPresenter;
import dvinc.yamblzhomeproject.ui.weather.WeatherPresenter;

public class TestComponent implements AppComponent {

    @Override
    public void inject(BGSyncJob bgSyncJob) {

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
