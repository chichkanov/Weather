package dvinc.yamblzhomeproject.ui.selectCity;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dvinc.yamblzhomeproject.data.model.predictions.Prediction;

interface SelectCityView extends MvpView {

    void setCityNameObservable();

    void showList(List<Prediction> predictions);

    void showError();

    void goToWeather();

    void clearText();
}
