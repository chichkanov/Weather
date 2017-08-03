package dvinc.yamblzhomeproject.repository.model.weather.dailyForecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temp {

    @SerializedName("day")
    @Expose
    private double day;
    @SerializedName("eve")
    @Expose
    private double eve;
    @SerializedName("max")
    @Expose
    private double max;
    @SerializedName("min")
    @Expose
    private double min;
    @SerializedName("morn")
    @Expose
    private double morn;
    @SerializedName("night")
    @Expose
    private double night;

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public double getEve() {
        return eve;
    }

    public void setEve(double eve) {
        this.eve = eve;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMorn() {
        return morn;
    }

    public void setMorn(double morn) {
        this.morn = morn;
    }

    public double getNight() {
        return night;
    }

    public void setNight(double night) {
        this.night = night;
    }
}
