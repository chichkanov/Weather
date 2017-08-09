package dvinc.yamblzhomeproject.di;

import javax.inject.Singleton;

import dagger.Component;
import dvinc.yamblzhomeproject.di.modules.ApplicationModule;
import dvinc.yamblzhomeproject.di.modules.NetworkModule;
import dvinc.yamblzhomeproject.di.modules.RepositoryModule;
import dvinc.yamblzhomeproject.net.background.BGSyncJob;
import dvinc.yamblzhomeproject.ui.base.MainPresenter;
import dvinc.yamblzhomeproject.ui.editCities.EditCitiesPresenter;
import dvinc.yamblzhomeproject.ui.selectCity.SelectCityPresenter;
import dvinc.yamblzhomeproject.ui.weather.WeatherPresenter;

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class, ApplicationModule.class})
public interface AppComponent {

    void inject(BGSyncJob bgSyncJob);

    MainPresenter getMainPresenter();

    WeatherPresenter getWeatherPresenter();

    SelectCityPresenter getSelectCityPresenter();

    EditCitiesPresenter getEditCitiesPresenter();
}
