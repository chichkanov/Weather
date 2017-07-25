package dvinc.yamblzhomeproject.ui.selectCity;

interface SelectCityPresenter<T extends SelectCityView> {

    void attachView(T view);

    void detachView();
}
