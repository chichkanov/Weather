package dvinc.yamblzhomeproject.ui.selectCity;

import dvinc.yamblzhomeproject.repository.SelectCityRepositoryImpl;
import io.reactivex.Observable;

interface SelectCityPresenter<T extends SelectCityView> {

    void attachView(T view, SelectCityRepositoryImpl repository);

    void detachView();

    void setObservable(Observable<CharSequence> disposable);
}
