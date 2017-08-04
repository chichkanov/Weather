package dvinc.yamblzhomeproject.db;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import dvinc.yamblzhomeproject.repository.model.predictions.predictionInfo.Location;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherCombiner;

@Entity(tableName = "cityWeather")
public class CityWeatherEntity {

    @PrimaryKey
    private String cityName;
    @Embedded
    private Location location;
    @Embedded
    private WeatherCombiner weatherCombiner;

    public WeatherCombiner getWeatherCombiner() {
        return weatherCombiner;
    }

    void setWeatherCombiner(WeatherCombiner weatherCombiner) {
        this.weatherCombiner = weatherCombiner;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    CityWeatherEntity() {
    }

    @Ignore
    public CityWeatherEntity(String cityName, WeatherCombiner weatherCombiner) {
        this.cityName = cityName;
        this.weatherCombiner = weatherCombiner;
    }
}
