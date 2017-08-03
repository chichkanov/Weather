package dvinc.yamblzhomeproject.repository.model.weather;
/*
 * Created by DV on Space 5 
 * 14.07.2017
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    @Expose
    private double speed;
    @SerializedName("deg")
    @Expose
    private double deg;
    @SerializedName("gust")
    @Expose
    private int gust;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getGust() {
        return gust;
    }

    public void setGust(int gust) {
        this.gust = gust;
    }

}
