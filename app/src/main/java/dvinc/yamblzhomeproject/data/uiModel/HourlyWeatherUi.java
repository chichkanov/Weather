package dvinc.yamblzhomeproject.data.uiModel;

public class HourlyWeatherUi {

    private String icon;
    private double temp;
    private long date;

    public HourlyWeatherUi() {
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
