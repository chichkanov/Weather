package dvinc.yamblzhomeproject.utils.converter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dvinc.yamblzhomeproject.data.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.data.uiModel.CurrentWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.DailyWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.HourlyWeatherUi;

@Singleton
public class WeatherConverter implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String UNITS_TEMP_KEY = "temp_units";
    private static final String UNITS_WIND_SPEED_KEY = "wind_speed_units";
    private static final String UNITS_PRESSURE_KEY = "pressure_units";

    private SharedPreferences prefs;
    private String tempValue;
    private String windValue;
    private String pressureValue;

    @Inject
    WeatherConverter(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.registerOnSharedPreferenceChangeListener(this);

        tempValue = prefs.getString(UNITS_TEMP_KEY, "0");
        pressureValue = prefs.getString(UNITS_PRESSURE_KEY, "0");
        windValue = prefs.getString(UNITS_WIND_SPEED_KEY, "0");
    }

    public WeatherCombiner convert(WeatherCombiner weatherCombiner) {
        weatherCombiner.setCurrentWeather(convertCurrentWeather(weatherCombiner.getCurrentWeather()));
        weatherCombiner.setDayWeather(convertDailyWeather(weatherCombiner.getDayWeather()));
        weatherCombiner.setHourWeather(convertHourlyWeather(weatherCombiner.getHourWeather()));
        return weatherCombiner;
    }

    private CurrentWeatherUi convertCurrentWeather(CurrentWeatherUi currentWeatherUi) {
        currentWeatherUi.setPressure(convertPressure(currentWeatherUi.getPressure()));
        currentWeatherUi.setTempMax(convertTemp(currentWeatherUi.getTempMax()));
        currentWeatherUi.setTempMin(convertTemp(currentWeatherUi.getTempMin()));
        currentWeatherUi.setTemp(convertTemp(currentWeatherUi.getTemp()));
        currentWeatherUi.setWindSpeed(convertWindSpeed(currentWeatherUi.getWindSpeed()));
        return currentWeatherUi;
    }

    private List<DailyWeatherUi> convertDailyWeather(List<DailyWeatherUi> dailyWeatherUi) {
        for (int i = 0; i < dailyWeatherUi.size(); i++) {
            dailyWeatherUi.get(i).setTempMax(convertTemp(dailyWeatherUi.get(i).getTempMax()));
            dailyWeatherUi.get(i).setTempMin(convertTemp(dailyWeatherUi.get(i).getTempMin()));
        }
        return dailyWeatherUi;
    }

    private List<HourlyWeatherUi> convertHourlyWeather(List<HourlyWeatherUi> hourlyWeatherUi) {
        for (int i = 0; i < hourlyWeatherUi.size(); i++) {
            hourlyWeatherUi.get(i).setTemp(convertTemp(hourlyWeatherUi.get(i).getTemp()));
        }
        return hourlyWeatherUi;
    }

    private int convertTemp(double value) {
        switch (tempValue) {
            case ConverterValues.TEMP_CELS: {
                return tempConvertKelvinToCels(value);
            }
            case ConverterValues.TEMP_FAR: {
                return tempConvertKelvinToFar(value);
            }
        }
        return 0;
    }

    private int convertPressure(double value) {
        if (pressureValue.equals(ConverterValues.PRESSURE_MM)) {
            return pressureConvertHpaToMmHg(value);
        }
        return (int) value;
    }

    private int convertWindSpeed(double value) {
        switch (windValue) {
            case ConverterValues.WIND_SPEED_KMH: {
                return windConvertMsToKmH(value);
            }
            case ConverterValues.WIND_SPEED_MILESH: {
                return windConvertMsToMilesH(value);
            }
        }
        return (int) value;
    }

    private int windConvertMsToKmH(double value) {
        return (int) (value * 3.6);
    }

    private int windConvertMsToMilesH(double value) {
        return (int) (value * 2.24);
    }

    private int tempConvertKelvinToCels(double value) {
        return (int) (value - 273);
    }

    private int tempConvertKelvinToFar(double value) {
        return (int) (1.8 * (value - 273) + 32);
    }

    private int pressureConvertHpaToMmHg(double value) {
        return (int) (value * 0.75);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        tempValue = prefs.getString(UNITS_TEMP_KEY, "0");
        pressureValue = prefs.getString(UNITS_PRESSURE_KEY, "0");
        windValue = prefs.getString(UNITS_WIND_SPEED_KEY, "0");
    }

    public String getTempValue() {
        return tempValue;
    }

    public String getWindValue() {
        return windValue;
    }

    public String getPressureValue() {
        return pressureValue;
    }
}
