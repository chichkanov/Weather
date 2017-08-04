package dvinc.yamblzhomeproject.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CityWeatherDao {

    @Query("SELECT * FROM cityWeather")
    List<CityWeatherEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(CityWeatherEntity... weatherEntities);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertNewCity(CityWeatherEntity weatherEntity);

    @Delete
    void delete(CityWeatherEntity weatherEntity);

}
