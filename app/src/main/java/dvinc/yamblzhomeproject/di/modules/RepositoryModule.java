package dvinc.yamblzhomeproject.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dvinc.yamblzhomeproject.repository.SelectCityRepository;
import dvinc.yamblzhomeproject.repository.SelectCityRepositoryImpl;
import dvinc.yamblzhomeproject.repository.WeatherRepository;
import dvinc.yamblzhomeproject.repository.WeatherRepositoryImpl;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    SelectCityRepository providesSelectCityRepository(){
        return new SelectCityRepositoryImpl();
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(){
        return new WeatherRepositoryImpl();
    }
}
