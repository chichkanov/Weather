package dvinc.yamblzhomeproject.ui.selectCity;

import dvinc.yamblzhomeproject.repository.model.predictions.Prediction;
import io.reactivex.Observable;

interface SelectCityPresenter<T extends SelectCityView> {

    void attachView(T view);

    void detachView();

    void setObservable(Observable<CharSequence> disposable);

    void citySelected(Prediction prediction);
}
