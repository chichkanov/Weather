package dvinc.yamblzhomeproject.di;

import dvinc.yamblzhomeproject.repository.SelectCityRepositoryImpl;
import dvinc.yamblzhomeproject.repository.WeatherRepositoryImpl;
import dvinc.yamblzhomeproject.ui.selectCity.SelectCityPresenter;
import dvinc.yamblzhomeproject.ui.weather.WeatherFragment;
import dvinc.yamblzhomeproject.ui.weather.WeatherPresenter;
import dvinc.yamblzhomeproject.utils.Settings;

/**
 * Created by chichkanov on 29.07.17.
 */

public class TestComponent implements AppComponent {

    @Override
    public void inject(SelectCityRepositoryImpl repository) {

    }

    @Override
    public void inject(Settings settings) {

    }

    @Override
    public void inject(WeatherFragment weatherFragment) {

    }

    @Override
    public void inject(WeatherRepositoryImpl weatherRepository) {

    }

    @Override
    public void inject(SelectCityPresenter selectCityPresenter) {

    }

    @Override
    public void inject(WeatherPresenter weatherPresenter) {

    }
}
