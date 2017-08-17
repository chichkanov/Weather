package dvinc.yamblzhomeproject.data.uiModel;

public class DailyWeatherUi {

    private String icon;
    private double tempMin;
    private double tempMax;

    public DailyWeatherUi() {
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }
}
