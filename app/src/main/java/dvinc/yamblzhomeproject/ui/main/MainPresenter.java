package dvinc.yamblzhomeproject.ui.main;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.data.repository.CitiesRepository;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.ui.about.AboutFragment;
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
        navigationManager.navigateTo(WeatherFragment.newInstance());
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
                    navigationManager.navigateTo(WeatherFragment.newInstance());
                });
        compositeDisposable.addAll(disposable);
    }

    void openAddCityActivity(MvpAppCompatActivity activity, Intent intent, int requestCode) {
        navigationManager.navigateTo(activity, intent, requestCode);
    }

    void openSettingsFragment() {
        navigationManager.navigateTo(SettingsFragment.newInstance());
    }

    void openEditCitiesFragment() {
        navigationManager.navigateTo(EditCitiesFragment.newInstance());
    }

    void openAboutFragment() {
        navigationManager.navigateTo(AboutFragment.newInstance());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.e("Destroyed");
        compositeDisposable.clear();
    }

    void onNewCityAdded() {
        navigationManager.navigateTo(WeatherFragment.newInstance());
    }
}
