package dvinc.yamblzhomeproject.db;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import dvinc.yamblzhomeproject.db.entities.CityEntity;
import io.reactivex.Flowable;

@android.arch.persistence.room.Dao
public interface Dao {

    @Query("SELECT * FROM cities")
    Flowable<List<CityEntity>> getAllCities();

    @Insert
    void insertNewCity(CityEntity cityEntity);

}
