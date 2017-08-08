package dvinc.yamblzhomeproject.di.modules;

import dagger.Module;
import dagger.Provides;
import dvinc.yamblzhomeproject.repository.MenuRepository;
import dvinc.yamblzhomeproject.repository.SelectCityRepository;
import dvinc.yamblzhomeproject.repository.WeatherRepository;
import dvinc.yamblzhomeproject.ui.base.MainPresenter;
import dvinc.yamblzhomeproject.ui.selectCity.SelectCityPresenter;
import dvinc.yamblzhomeproject.ui.weather.WeatherPresenter;

@Module
public class PresenterModule {

    @Provides
    MainPresenter provideMainPresenter(MenuRepository menuRepository) {
        return new MainPresenter(menuRepository);
    }

    @Provides
    WeatherPresenter provideWeatherPresenter(WeatherRepository weatherRepository) {
        return new WeatherPresenter(weatherRepository);
    }

    @Provides
    SelectCityPresenter provideSelectCityPresenter(SelectCityRepository selectCityRepository) {
        return new SelectCityPresenter(selectCityRepository);
    }
}
