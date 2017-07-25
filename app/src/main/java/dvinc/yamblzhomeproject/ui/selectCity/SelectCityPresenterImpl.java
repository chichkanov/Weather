package dvinc.yamblzhomeproject.ui.selectCity;

class SelectCityPresenterImpl<T extends SelectCityView> implements SelectCityPresenter<T> {

    private T view;

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
