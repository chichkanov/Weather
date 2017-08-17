package dvinc.yamblzhomeproject.db.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import dvinc.yamblzhomeproject.data.model.weather.WeatherCombiner;

@Entity(tableName = "weather")
public class WeatherEntity {

    @PrimaryKey
    private String placeId;

    @Embedded
    private WeatherCombiner weatherCombiner;

    @Ignore
    public WeatherEntity(String placeId, WeatherCombiner weatherCombiner) {
        this.placeId = placeId;
        this.weatherCombiner = weatherCombiner;
    }

    public WeatherEntity() {
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
