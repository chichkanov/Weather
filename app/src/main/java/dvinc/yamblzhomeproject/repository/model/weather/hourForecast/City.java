package dvinc.yamblzhomeproject.repository.model.weather.hourForecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import dvinc.yamblzhomeproject.repository.model.weather.core.Coord;

class City {

    @SerializedName("coord")
    @Expose
    private Coord coord;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
