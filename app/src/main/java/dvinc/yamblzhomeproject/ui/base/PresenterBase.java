package dvinc.yamblzhomeproject.ui.base;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

public interface PresenterBase<T extends ViewBase> {

    void attachView(T mvpView);

    void detachView();

    void openWeatherFragment();

    void openSettingsFragment();

    void openAboutFragment();

    void openSelectCityFragment();
}