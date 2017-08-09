package dvinc.yamblzhomeproject.data.uiModel;

public class DailyWeatherUi {

    private String icon;
    private int tempMin;
    private int tempMax;

    public DailyWeatherUi() {
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getTempMin() {
        return tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

    public int getTempMax() {
        return tempMax;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }
}
