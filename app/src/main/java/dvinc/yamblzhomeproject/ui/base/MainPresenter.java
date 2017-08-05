package dvinc.yamblzhomeproject.ui.base;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import dvinc.yamblzhomeproject.repository.MenuRepository;
import dvinc.yamblzhomeproject.ui.about.AboutFragment;
import dvinc.yamblzhomeproject.ui.selectCity.SelectCityFragment;
import dvinc.yamblzhomeproject.ui.settings.SettingsFragment;
import dvinc.yamblzhomeproject.ui.weather.WeatherFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private MenuRepository menuRepository;

    public MainPresenter(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    void openWeatherFragment(String title) {
        getViewState().showFragment(WeatherFragment.newInstanse(title));
    }

    void openSettingsFragment() {
        getViewState().showFragment(new SettingsFragment());
    }

    void openAboutFragment() {
        getViewState().showAboutFragment(new AboutFragment());
    }

    void openSelectCityFragment() {
        getViewState().showFragment(SelectCityFragment.newInstance());
    }

    void observeMenuChanges() {
        menuRepository.updateMenu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> {
                    Log.i("MENU", "changed");
                    getViewState().initCitiesInMenu(next);
                });
    }
}
