package dvinc.yamblzhomeproject.repository;

import java.util.List;

import dvinc.yamblzhomeproject.db.AppDatabase;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import io.reactivex.Flowable;

public class MenuRepositoryImpl implements MenuRepository {

    private AppDatabase appDatabase;

    public MenuRepositoryImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public Flowable<List<CityEntity>> updateMenu() {
        return appDatabase.dao().getAllCities().distinctUntilChanged();
    }
}
