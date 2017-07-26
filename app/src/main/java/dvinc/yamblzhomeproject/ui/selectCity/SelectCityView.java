package dvinc.yamblzhomeproject.ui.selectCity;

import java.util.List;

import dvinc.yamblzhomeproject.repository.model.predictions.Prediction;

interface SelectCityView {

    void setCityNameObservable();

    void showList(List<Prediction> predictions);

    void showError();
}
