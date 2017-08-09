package dvinc.yamblzhomeproject.data.repository;

import android.util.Log;

import java.util.List;

import dvinc.yamblzhomeproject.db.AppDatabase;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class MenuRepositoryImpl implements MenuRepository {

    private AppDatabase appDatabase;

    public MenuRepositoryImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public Flowable<List<CityEntity>> updateMenu() {
        Log.i("MenuRepo", "Update Menu");
        return appDatabase.cityDao().getAllCities();
    }

    @Override
    public Single<List<CityEntity>> getMenuItems() {
        return Single.defer(() -> Single.just(appDatabase.cityDao().getAllCitiesSync()));
    }

    @Override
    public Completable setActiveCity(CityEntity cityEntity) {
        return Completable.fromAction(() -> {
            CityEntity prevActive = appDatabase.cityDao().getActiveCity();
            if (prevActive != null) {
                prevActive.setActive(false);
                appDatabase.cityDao().updateCity(prevActive);
            }
            cityEntity.setActive(true);
            appDatabase.cityDao().updateCity(cityEntity);
        });
    }
}
