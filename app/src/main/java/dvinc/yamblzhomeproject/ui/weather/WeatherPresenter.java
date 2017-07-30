package dvinc.yamblzhomeproject.ui.weather;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.gson.Gson;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.repository.WeatherRepositoryImpl;
import dvinc.yamblzhomeproject.utils.Settings;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    @Inject
    WeatherRepositoryImpl repository;

    @Inject
    Settings settings;

    private Disposable dataSubscription;

    WeatherPresenter() {
        App.getComponent().inject(this);
    }

    void getWeather() {
        dataSubscription = repository.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(next -> {
                            getViewState().updateWeatherParameters(next);
                            settings.saveWeather(new Gson().toJson(next));
                        },
                        error -> {
                            Log.i("Error", error.getMessage());
                            getViewState().showError();
                        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dataSubscription != null) {
            dataSubscription.dispose();
        }
    }
}
