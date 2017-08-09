package dvinc.yamblzhomeproject.db.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import dvinc.yamblzhomeproject.db.entities.WeatherEntity;
import io.reactivex.Maybe;

@android.arch.persistence.room.Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeather(WeatherEntity weatherEntity);

    @Query("SELECT * FROM weather WHERE placeid LIKE :cityId LIMIT 1")
    Maybe<WeatherEntity> getWeatherForCityId(String cityId);

    @Delete
    void deleteWeather(WeatherEntity weatherEntity);
}
