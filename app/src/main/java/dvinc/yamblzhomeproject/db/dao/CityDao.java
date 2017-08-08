package dvinc.yamblzhomeproject.db.dao;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import dvinc.yamblzhomeproject.db.entities.CityEntity;
import io.reactivex.Flowable;

@android.arch.persistence.room.Dao
public interface CityDao {

    @Query("SELECT * FROM cities")
    Flowable<List<CityEntity>> getAllCities();

    @Query("SELECT * FROM cities")
    List<CityEntity> getAllCitiesSync();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewCity(CityEntity cityEntity);

    @Update
    void updateCity(CityEntity cityEntity);

    @Query("SELECT * FROM cities WHERE cityId LIKE :cityId LIMIT 1")
    CityEntity getCityWithId(String cityId);

    @Query("SELECT * FROM cities WHERE isActive IS 1 LIMIT 1")
    CityEntity getActiveCity();

    @Query("SELECT * FROM cities WHERE isActive IS 1 LIMIT 1")
    Flowable<CityEntity> getActiveCityFlowable();
}
