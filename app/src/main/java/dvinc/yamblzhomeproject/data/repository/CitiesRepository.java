package dvinc.yamblzhomeproject.data.repository;

import java.util.List;

import dvinc.yamblzhomeproject.db.entities.CityEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface CitiesRepository {

    Completable removeCity(CityEntity cityEntity);

    Flowable<List<CityEntity>> updateMenu();

    Single<List<CityEntity>> getMenuItems();

    Completable setActiveCity(CityEntity cityEntity);

    Flowable<CityEntity> getActiveCity();
}
