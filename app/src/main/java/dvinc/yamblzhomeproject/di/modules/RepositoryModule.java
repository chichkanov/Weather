package dvinc.yamblzhomeproject.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dvinc.yamblzhomeproject.db.AppDatabase;
import dvinc.yamblzhomeproject.net.PredictionsApi;
import dvinc.yamblzhomeproject.net.WeatherApi;
import dvinc.yamblzhomeproject.repository.MenuRepository;
import dvinc.yamblzhomeproject.repository.MenuRepositoryImpl;
import dvinc.yamblzhomeproject.repository.SelectCityRepository;
import dvinc.yamblzhomeproject.repository.SelectCityRepositoryImpl;
import dvinc.yamblzhomeproject.repository.WeatherRepository;
import dvinc.yamblzhomeproject.repository.WeatherRepositoryImpl;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    SelectCityRepository providesSelectCityRepository(PredictionsApi predictionsApi, AppDatabase appDatabase) {
        return new SelectCityRepositoryImpl(predictionsApi, appDatabase);
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(WeatherApi weatherApi, AppDatabase appDatabase) {
        return new WeatherRepositoryImpl(weatherApi, appDatabase);
    }

    @Provides
    @Singleton
    MenuRepository provideMenuRepository(AppDatabase appDatabase) {
        return new MenuRepositoryImpl(appDatabase);
    }
}
