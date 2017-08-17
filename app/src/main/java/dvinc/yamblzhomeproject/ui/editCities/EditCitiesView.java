package dvinc.yamblzhomeproject.ui.editCities;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import dvinc.yamblzhomeproject.db.entities.CityEntity;

@StateStrategyType(SkipStrategy.class)
interface EditCitiesView extends MvpView {

    void showError();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showCitiesList(List<CityEntity> cityEntities);

    void onItemRemoved(CityEntity cityEntity);
}
