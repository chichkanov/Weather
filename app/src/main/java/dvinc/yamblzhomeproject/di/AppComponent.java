package dvinc.yamblzhomeproject.di;

import javax.inject.Singleton;

import dagger.Component;
import dvinc.yamblzhomeproject.di.modules.ApplicationModule;
import dvinc.yamblzhomeproject.di.modules.NetworkModule;
import dvinc.yamblzhomeproject.di.modules.RepositoryModule;
import dvinc.yamblzhomeproject.repository.SelectCityRepositoryImpl;
import dvinc.yamblzhomeproject.repository.WeatherRepositoryImpl;
import dvinc.yamblzhomeproject.ui.weather.MvpWeatherFragment;
import dvinc.yamblzhomeproject.utils.Settings;

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class, ApplicationModule.class})
public interface AppComponent {

    void inject(SelectCityRepositoryImpl repository);

    SelectCityRepositoryImpl getCityRepository();

    void inject(Settings settings);

    Settings getSettings();

    void inject(MvpWeatherFragment mvpWeatherFragment);

    void inject(WeatherRepositoryImpl weatherRepository);
}
