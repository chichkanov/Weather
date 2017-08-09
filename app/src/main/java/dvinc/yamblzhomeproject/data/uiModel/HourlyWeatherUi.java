package dvinc.yamblzhomeproject.data.uiModel;

public class HourlyWeatherUi {

    private String icon;
    private int temp;
    private long date;

    public HourlyWeatherUi() {
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
