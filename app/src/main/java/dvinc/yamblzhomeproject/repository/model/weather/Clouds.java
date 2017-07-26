package dvinc.yamblzhomeproject.repository.model.weather;
/*
 * Created by DV on Space 5
 * 14.07.2017
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Clouds {

    @SerializedName("all")
    @Expose
    private Integer all;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

}
