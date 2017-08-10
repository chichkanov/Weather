package dvinc.yamblzhomeproject.ui.base;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dvinc.yamblzhomeproject.db.entities.CityEntity;

interface MainView extends MvpView {

    void initCitiesInMenu(List<CityEntity> cities, boolean fireOnClick);
}
