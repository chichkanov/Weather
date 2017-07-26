package dvinc.yamblzhomeproject.di;

import javax.inject.Singleton;

import dagger.Component;
import dvinc.yamblzhomeproject.di.modules.NetworkModule;
import dvinc.yamblzhomeproject.di.modules.RepositoryModule;
import dvinc.yamblzhomeproject.repository.SelectCityRepositoryImpl;

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class})
public interface AppComponent {

    void inject(SelectCityRepositoryImpl repository);

    SelectCityRepositoryImpl getCityRepository();
}
