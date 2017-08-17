package dvinc.yamblzhomeproject.ui.selectCity;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import dvinc.yamblzhomeproject.data.model.predictions.Prediction;

@StateStrategyType(AddToEndSingleStrategy.class)
interface SelectCityView extends MvpView {

    void setCityNameObservable();

    void showList(List<Prediction> predictions);

    void showError();

    void goToWeather();

    void clearText();
}
