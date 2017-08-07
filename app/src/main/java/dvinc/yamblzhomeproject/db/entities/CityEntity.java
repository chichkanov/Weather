package dvinc.yamblzhomeproject.db.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import dvinc.yamblzhomeproject.repository.model.predictions.predictionInfo.Location;

@Entity(tableName = "cities")
public class CityEntity {

    @PrimaryKey
    private String cityId;

    @Embedded
    private Location location;

    private String cityTitle;

    private boolean isActive;

    @Ignore
    public CityEntity(Location location, String cityId, String cityTitle, boolean isActive) {
        this.location = location;
        this.cityId = cityId;
        this.cityTitle = cityTitle;
        this.isActive = isActive;
    }

    public CityEntity() {
    }

    public Location getLocation() {

        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
