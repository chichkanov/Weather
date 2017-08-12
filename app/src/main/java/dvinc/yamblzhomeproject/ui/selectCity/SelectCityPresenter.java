package dvinc.yamblzhomeproject.ui.selectCity;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.data.model.predictions.Prediction;
import dvinc.yamblzhomeproject.data.repository.SelectCityRepository;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SelectCityPresenter extends MvpPresenter<SelectCityView> {

    private static final int API_CALL_DELAY = 400;

    private Disposable subscriptionPlace;
    private Disposable subscriptionPlaceCoords;
    private SelectCityRepository repository;

    @Inject
    public SelectCityPresenter(SelectCityRepository selectCityRepository) {
        this.repository = selectCityRepository;
    }

    void setObservable(Observable<CharSequence> observable) {
        subscriptionPlace = observable
                .subscribeOn(Schedulers.io())
                .debounce(API_CALL_DELAY, TimeUnit.MILLISECONDS)
                .switchMap(charSequence -> repository.getPrediction(charSequence.toString()).subscribeOn(Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> getViewState().showList(next.getPredictions()),
                        error -> getViewState().showError());
    }

    void citySelected(Prediction item) {
        Log.i("SelectCity", "City Selected");
        subscriptionPlaceCoords = repository
                .getPredictionCoord(item.getPlaceId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> repository.saveCity(next, item.getStructuredFormatting().getMainText(), item.getPlaceId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> getViewState().goToWeather(), error -> getViewState().showError()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscriptionPlace != null) {
            subscriptionPlace.dispose();
        }
        if (subscriptionPlaceCoords != null) {
            subscriptionPlaceCoords.dispose();
        }
    }

    void clearButtonCLicked(String text) {
        if (text.length() == 0) {
            getViewState().goToWeather();
        } else {
            getViewState().clearText();
        }
    }
}
