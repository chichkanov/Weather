package dvinc.yamblzhomeproject.ui.base;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.data.repository.CitiesRepository;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.ui.editCities.EditCitiesFragment;
import dvinc.yamblzhomeproject.ui.navigation.NavigationManager;
import dvinc.yamblzhomeproject.ui.settings.SettingsFragment;
import dvinc.yamblzhomeproject.ui.weather.WeatherFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private CitiesRepository citiesRepository;

    private Disposable menuChangeSubscription;
    private Disposable menuActiveCity;
    private NavigationManager navigationManager;

    @Inject
    MainPresenter(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
        getCities();
        observeMenuChanges();
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
    }

    void setNavigationManager(FragmentManager fragmentManager) {
        navigationManager = new NavigationManager(fragmentManager, R.id.fragmentContainer);
    }

    void openWeatherFragment(CityEntity cityEntity) {
        menuActiveCity = citiesRepository.setActiveCity(cityEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> navigationManager.navigateTo(WeatherFragment.newInstance(cityEntity.getCityTitle())));
    }

    void openSettingsFragment() {
        navigationManager.navigateTo(SettingsFragment.newInstance());
    }

    void openEditCitiesFragment() {
        navigationManager.navigateTo(EditCitiesFragment.newInstance());
    }

    void getCities() {
        menuChangeSubscription = citiesRepository.getMenuItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> getViewState().initCitiesInMenu(next, true));
    }

    private void observeMenuChanges() {
        menuChangeSubscription = citiesRepository.updateMenu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> {
                    Log.i("MENU", "changed");
                    getViewState().initCitiesInMenu(next, false);
                });
    }

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
