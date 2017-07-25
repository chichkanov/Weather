package dvinc.yamblzhomeproject.ui.base;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import dvinc.yamblzhomeproject.ui.about.AboutFragment;
import dvinc.yamblzhomeproject.ui.settings.MvpSettingsFragment;
import dvinc.yamblzhomeproject.ui.weather.MvpWeatherFragment;

class PresenterBaseImpl<T extends ViewBase> implements PresenterBase<T> {

    private T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void openWeatherFragment() {
        view.showFragment(new MvpWeatherFragment());
    }

    @Override
    public void openSettingsFragment() {
        view.showFragment(new MvpSettingsFragment());
    }

    @Override
    public void openAboutFragment() {
        view.showAboutFragment(new AboutFragment());
    }

}
