package dvinc.yamblzhomeproject.ui.selectCity;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.repository.SelectCityRepositoryImpl;
import dvinc.yamblzhomeproject.repository.model.predictions.Prediction;
import dvinc.yamblzhomeproject.utils.Settings;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SelectCityPresenter extends MvpPresenter<SelectCityView> {

    private static final int API_CALL_DELAY = 400;

    private Disposable subscriptionPlace;
    private Disposable subscriptionPlaceCoords;

    @Inject
    SelectCityRepositoryImpl repository;

    @Inject
    Settings settings;

    SelectCityPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    public void detachView(SelectCityView view) {
        super.detachView(view);
        if (subscriptionPlace != null) {
            subscriptionPlace.dispose();
        }
        if (subscriptionPlaceCoords != null) {
            subscriptionPlaceCoords.dispose();
        }
    }

    void setObservable(Observable<CharSequence> observable) {
        this.subscriptionPlace = observable
                .subscribeOn(Schedulers.io())
                .debounce(API_CALL_DELAY, TimeUnit.MILLISECONDS)
                .switchMap(charSequence -> repository.getPrediction(charSequence.toString()).subscribeOn(Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> getViewState().showList(next.getPredictions()),
                        error -> getViewState().showError());
    }

    void citySelected(Prediction item) {
        subscriptionPlaceCoords = repository
                .getPredictionCoord(item.getPlaceId())
                .subscribeOn(Schedulers.io())
                .doOnNext(next -> repository.saveCity(next, item.getStructuredFormatting().getMainText(), item.getPlaceId()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> {
                            String city = item.getDescription();
                            // Add city do Room db here
                            settings.setCurrentCity(city);
                            settings.setCurrentCityLocationLong(next.getResult().getGeometry().getLocation().getLongitude());
                            settings.setCurrentCityLocationLat(next.getResult().getGeometry().getLocation().getLatitude());
                            getViewState().goToWeather();
                        },
                        error -> getViewState().showError());
    }

    void clearButtonCLicked(String text) {
        if (text.length() == 0) {
            getViewState().goToWeather();
        } else {
            getViewState().clearText();
        }
    }
}
