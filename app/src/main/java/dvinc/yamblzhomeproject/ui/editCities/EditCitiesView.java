package dvinc.yamblzhomeproject.ui.editCities;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dvinc.yamblzhomeproject.db.entities.CityEntity;

interface EditCitiesView extends MvpView {

    void showError();

    void showCitiesList(List<CityEntity> cityEntities);

    void onItemRemoved(CityEntity cityEntity);
}
