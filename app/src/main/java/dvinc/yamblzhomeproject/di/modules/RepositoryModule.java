package dvinc.yamblzhomeproject.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dvinc.yamblzhomeproject.repository.SelectCityRepositoryImpl;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    SelectCityRepositoryImpl providesSelectCityRepository(){
        return new SelectCityRepositoryImpl();
    }
}
