package dvinc.yamblzhomeproject.ui.main;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import dvinc.yamblzhomeproject.db.entities.CityEntity;

@StateStrategyType(AddToEndSingleStrategy.class)
interface MainView extends MvpView {

    void initCitiesInMenu(List<CityEntity> cities, boolean fireOnClick);
}
