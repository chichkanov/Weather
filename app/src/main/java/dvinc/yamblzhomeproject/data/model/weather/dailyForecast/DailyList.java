package dvinc.yamblzhomeproject.data.model.weather.dailyForecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import dvinc.yamblzhomeproject.data.model.weather.core.Weather;

public class DailyList {

    @SerializedName("clouds")
    @Expose
    private int cloudsDaily;
    @SerializedName("deg")
    @Expose
    private int degDaily;
    @SerializedName("humidity")
    @Expose
    private int humidityDaily;
    @SerializedName("pressure")
    @Expose
    private double pressureDaily;
    @SerializedName("speed")
    @Expose
    private double speedDaily;
    @SerializedName("temp")
    @Expose
    private Temp tempDaily;
    @SerializedName("weather")
    @Expose
    private java.util.List<Weather> weatherDaily = null;
    @SerializedName("rain")
    @Expose
    private double rainDaily;

    public int getCloudsDaily() {
        return cloudsDaily;
    }

    public void setCloudsDaily(int cloudsDaily) {
        this.cloudsDaily = cloudsDaily;
    }

    public int getDegDaily() {
        return degDaily;
    }

    public void setDegDaily(int degDaily) {
        this.degDaily = degDaily;
    }

    public int getHumidityDaily() {
        return humidityDaily;
    }

    public void setHumidityDaily(int humidityDaily) {
        this.humidityDaily = humidityDaily;
    }

    public double getPressureDaily() {
        return pressureDaily;
    }

    public void setPressureDaily(double pressureDaily) {
        this.pressureDaily = pressureDaily;
    }

    public double getSpeedDaily() {
        return speedDaily;
    }

    public void setSpeedDaily(double speedDaily) {
        this.speedDaily = speedDaily;
    }

    public Temp getTempDaily() {
        return tempDaily;
    }

    public void setTempDaily(Temp tempDaily) {
        this.tempDaily = tempDaily;
    }

    public List<Weather> getWeatherDaily() {
        return weatherDaily;
    }

    public void setWeatherDaily(List<Weather> weatherDaily) {
        this.weatherDaily = weatherDaily;
    }

    public double getRainDaily() {
        return rainDaily;
    }

    public void setRainDaily(double rainDaily) {
        this.rainDaily = rainDaily;
    }

    public DailyList() {
    }
}
