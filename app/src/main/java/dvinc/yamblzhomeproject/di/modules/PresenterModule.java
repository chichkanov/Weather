package dvinc.yamblzhomeproject.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dvinc.yamblzhomeproject.repository.MenuRepository;
import dvinc.yamblzhomeproject.ui.base.MainPresenter;

@Module
public class PresenterModule {

    @Provides
    @Singleton
    MainPresenter provideMainPresenter(MenuRepository menuRepository) {
        return new MainPresenter(menuRepository);
    }
}
