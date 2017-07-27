package dvinc.yamblzhomeproject.di;

import javax.inject.Singleton;

import dagger.Component;
import dvinc.yamblzhomeproject.di.modules.ApplicationModule;
import dvinc.yamblzhomeproject.di.modules.NetworkModule;
import dvinc.yamblzhomeproject.di.modules.RepositoryModule;
import dvinc.yamblzhomeproject.repository.SelectCityRepositoryImpl;
import dvinc.yamblzhomeproject.repository.WeatherRepositoryImpl;
import dvinc.yamblzhomeproject.ui.selectCity.SelectCityPresenter;
import dvinc.yamblzhomeproject.ui.weather.WeatherFragment;
import dvinc.yamblzhomeproject.utils.Settings;

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class, ApplicationModule.class})
public interface AppComponent {

    void inject(SelectCityRepositoryImpl repository);

    void inject(Settings settings);

    void inject(WeatherFragment weatherFragment);

    void inject(WeatherRepositoryImpl weatherRepository);

    void inject(SelectCityPresenter selectCityPresenter);
}
