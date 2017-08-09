package dvinc.yamblzhomeproject.ui.base;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import dvinc.yamblzhomeproject.data.repository.MenuRepository;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.ui.about.AboutFragment;
import dvinc.yamblzhomeproject.ui.selectCity.SelectCityFragment;
import dvinc.yamblzhomeproject.ui.settings.SettingsFragment;
import dvinc.yamblzhomeproject.ui.weather.WeatherFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private MenuRepository menuRepository;

    private Disposable menuChangeSubscription;
    private Disposable menuActiveCity;

    public MainPresenter(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getCities();
    }

    void openWeatherFragment(CityEntity cityEntity) {
        menuActiveCity = menuRepository.setActiveCity(cityEntity)
                .subscribeOn(Schedulers.io())
                .subscribe();

        getViewState().showFragment(WeatherFragment.newInstance(cityEntity.getCityTitle()));
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

    private void getCities() {
        menuChangeSubscription = menuRepository.getMenuItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> getViewState().initCitiesInMenu(next));
    }

    /*private void observeMenuChanges() {
        menuChangeSubscription = menuRepository.updateMenu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> {
                    Log.i("MENU", "changed");
                    getViewState().initCitiesInMenu(next);
                });
    }*/

    @Override
    public void detachView(MainView view) {
        super.detachView(view);
        if (menuChangeSubscription != null) {
            menuChangeSubscription.dispose();
        }
        if (menuActiveCity != null) {
            menuActiveCity.dispose();
        }
    }
}
