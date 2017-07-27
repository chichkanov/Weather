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

    private Disposable subscription;

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
        subscription.dispose();
    }

    void setObservable(Observable<CharSequence> observable) {
        this.subscription = observable
                .subscribeOn(Schedulers.io())
                .debounce(API_CALL_DELAY, TimeUnit.MILLISECONDS)
                .switchMap(charSequence -> repository.getPrediction(charSequence.toString()).subscribeOn(Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> getViewState().showList(next.getPredictions()),
                        error -> getViewState().showError());
    }

    void citySelected(Prediction item) {
        String city = item.getDescription();
        settings.setCurrentCity(city);
        getViewState().goToWeather();
    }
}
