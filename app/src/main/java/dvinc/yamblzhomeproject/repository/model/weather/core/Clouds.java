package dvinc.yamblzhomeproject.repository.model.weather.core;
/*
 * Created by DV on Space 5
 * 14.07.2017
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds {

    @SerializedName("all")
    @Expose
    private int all;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public Clouds() {
    }
}
