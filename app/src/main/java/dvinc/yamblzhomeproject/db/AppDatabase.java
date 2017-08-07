package dvinc.yamblzhomeproject.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import dvinc.yamblzhomeproject.db.dao.CityDao;
import dvinc.yamblzhomeproject.db.dao.WeatherDao;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.db.entities.WeatherEntity;

@Database(entities = {WeatherEntity.class, CityEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CityDao cityDao();
    public abstract WeatherDao weatherDao();
}
