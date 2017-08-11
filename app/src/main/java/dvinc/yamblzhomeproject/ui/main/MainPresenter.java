package dvinc.yamblzhomeproject.ui.main;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.support.v4.app.FragmentManager;

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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private CitiesRepository citiesRepository;

    private NavigationManager navigationManager;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    MainPresenter(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getCities(true);
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        observeMenuChanges();
    }

    void setNavigationManager(FragmentManager fragmentManager) {
        navigationManager = new NavigationManager(fragmentManager, R.id.fragmentContainer);
    }

    void openWeatherFragment(CityEntity cityEntity) {
        Timber.d("Open weather fragment called");
        Disposable disposable = citiesRepository.setActiveCity(cityEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Timber.d("Navigating to weather fragment");
                    navigationManager.navigateTo(WeatherFragment.newInstance(cityEntity.getCityTitle()));
                });
        compositeDisposable.addAll(disposable);
    }

    void openSettingsFragment() {
        navigationManager.navigateTo(SettingsFragment.newInstance());
    }

    void openEditCitiesFragment() {
        navigationManager.navigateTo(EditCitiesFragment.newInstance());
    }

    private void observeMenuChanges() {
        Disposable disposable = citiesRepository.updateMenu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> {
                    Timber.d("Cities loaded");
                    getViewState().initCitiesInMenu(next, false);
                });
        compositeDisposable.add(disposable);
    }

    void getCities(boolean fireOnClick) {
        Disposable disposable = citiesRepository.getMenuItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> {
                    Timber.d("Cities loaded");
                    getViewState().initCitiesInMenu(next, fireOnClick);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void detachView(MainView view) {
        super.detachView(view);
        compositeDisposable.clear();
    }

}
