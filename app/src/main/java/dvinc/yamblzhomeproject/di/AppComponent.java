package dvinc.yamblzhomeproject.di;

import javax.inject.Singleton;

import dagger.Component;
import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.di.modules.ApplicationModule;
import dvinc.yamblzhomeproject.di.modules.BackgroundModule;
import dvinc.yamblzhomeproject.di.modules.NetworkModule;
import dvinc.yamblzhomeproject.di.modules.RepositoryModule;
import dvinc.yamblzhomeproject.net.background.AutoUpdateJob;
import dvinc.yamblzhomeproject.ui.editCities.EditCitiesPresenter;
import dvinc.yamblzhomeproject.ui.main.MainPresenter;
import dvinc.yamblzhomeproject.ui.selectCity.SelectCityPresenter;
import dvinc.yamblzhomeproject.ui.weather.WeatherPresenter;

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class, ApplicationModule.class, BackgroundModule.class})
public interface AppComponent {

    void inject(AutoUpdateJob autoUpdateJob);

    MainPresenter getMainPresenter();

    WeatherPresenter getWeatherPresenter();

    SelectCityPresenter getSelectCityPresenter();

    EditCitiesPresenter getEditCitiesPresenter();

    void inject(App app);
}
