package dvinc.yamblzhomeproject.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dvinc.yamblzhomeproject.data.repository.MenuRepository;
import dvinc.yamblzhomeproject.data.repository.MenuRepositoryImpl;
import dvinc.yamblzhomeproject.data.repository.SelectCityRepository;
import dvinc.yamblzhomeproject.data.repository.SelectCityRepositoryImpl;
import dvinc.yamblzhomeproject.data.repository.WeatherRepository;
import dvinc.yamblzhomeproject.data.repository.WeatherRepositoryImpl;
import dvinc.yamblzhomeproject.data.uiModel.mapper.WeatherMapper;
import dvinc.yamblzhomeproject.db.AppDatabase;
import dvinc.yamblzhomeproject.net.PredictionsApi;
import dvinc.yamblzhomeproject.net.WeatherApi;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    SelectCityRepository providesSelectCityRepository(PredictionsApi predictionsApi, AppDatabase appDatabase) {
        return new SelectCityRepositoryImpl(predictionsApi, appDatabase);
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(WeatherApi weatherApi, AppDatabase appDatabase, WeatherMapper weatherMapper) {
        return new WeatherRepositoryImpl(weatherApi, appDatabase, weatherMapper);
    }

    @Provides
    @Singleton
    MenuRepository provideMenuRepository(AppDatabase appDatabase) {
        return new MenuRepositoryImpl(appDatabase);
    }
}
