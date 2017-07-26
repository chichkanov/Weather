package dvinc.yamblzhomeproject.ui.selectCity;

import io.reactivex.Observable;

interface SelectCityPresenter<T extends SelectCityView> {

    void attachView(T view);

    void detachView();

    void setObservable(Observable<CharSequence> disposable);
}
