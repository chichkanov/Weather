package dvinc.yamblzhomeproject.ui.selectCity;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.repository.SelectCityRepositoryImpl;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

class SelectCityPresenterImpl<T extends SelectCityView> implements SelectCityPresenter<T> {

    private static final int API_CALL_DELAY = 400;

    private T view;
    private Disposable subscription;

    SelectCityRepositoryImpl repository;

    SelectCityPresenterImpl() {
        repository = App.getComponent().getCityRepository();
    }

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        subscription.dispose();
        view = null;
    }

    @Override
    public void setObservable(Observable<CharSequence> observable) {
        this.subscription = observable
                .subscribeOn(Schedulers.io())
                .debounce(API_CALL_DELAY, TimeUnit.MILLISECONDS)
                .switchMap(charSequence -> repository.getPrediction(charSequence.toString()).subscribeOn(Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> {
                            if (view != null) {
                                view.showList(next.getPredictions());
                            }
                        },
                        error -> {
                            Log.i("Error", error.getMessage());
                            if (view != null) view.showError();
                        });
    }
}
