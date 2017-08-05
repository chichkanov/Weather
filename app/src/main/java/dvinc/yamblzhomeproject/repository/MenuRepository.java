package dvinc.yamblzhomeproject.repository;

import java.util.List;

import dvinc.yamblzhomeproject.db.entities.CityEntity;
import io.reactivex.Flowable;

public interface MenuRepository {

    Flowable<List<CityEntity>> updateMenu();
}
