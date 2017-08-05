package dvinc.yamblzhomeproject.db.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import dvinc.yamblzhomeproject.repository.model.weather.WeatherCombiner;

@Entity(tableName = "cityWeather")
public class CityWeatherEntity {

    @PrimaryKey(autoGenerate = true)
    private int mainid;

    private String placeId;

    @Embedded
    private WeatherCombiner weatherCombiner;

    @Ignore
    public CityWeatherEntity(String placeId, WeatherCombiner weatherCombiner) {
        this.placeId = placeId;
        this.weatherCombiner = weatherCombiner;
    }

    public CityWeatherEntity() {
    }

    public int getMainid() {
        return mainid;
    }

    public void setMainid(int mainid) {
        this.mainid = mainid;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public WeatherCombiner getWeatherCombiner() {
        return weatherCombiner;
    }

    public void setWeatherCombiner(WeatherCombiner weatherCombiner) {
        this.weatherCombiner = weatherCombiner;
    }
}
