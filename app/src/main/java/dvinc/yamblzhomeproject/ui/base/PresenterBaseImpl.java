package dvinc.yamblzhomeproject.ui.base;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import dvinc.yamblzhomeproject.ui.about.AboutFragment;
import dvinc.yamblzhomeproject.ui.selectCity.SelectCityFragment;
import dvinc.yamblzhomeproject.ui.settings.SettingsFragment;
import dvinc.yamblzhomeproject.ui.weather.WeatherFragment;

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
        view.showFragment(new WeatherFragment());
    }

    @Override
    public void openSettingsFragment() {
        view.showFragment(new SettingsFragment());
    }

    @Override
    public void openAboutFragment() {
        view.showAboutFragment(new AboutFragment());
    }

    @Override
    public void openSelectCityFragment() {
        view.showFragment(SelectCityFragment.newInstance());
    }
}
