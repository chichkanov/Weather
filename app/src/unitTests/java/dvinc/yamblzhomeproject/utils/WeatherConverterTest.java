package dvinc.yamblzhomeproject.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.List;

import dvinc.yamblzhomeproject.data.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.data.uiModel.CurrentWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.DailyWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.HourlyWeatherUi;
import dvinc.yamblzhomeproject.utils.converter.ConverterValues;
import dvinc.yamblzhomeproject.utils.converter.WeatherConverter;

@RunWith(RobolectricTestRunner.class)
public class WeatherConverterTest {

    private static final double DEFAULT_TEMP_VALUE = 30;
    private static final double DEFAULT_WIND_VALUE = 10;
    private static final double DEFAULT_PRESSURE_VALUE = 700;

    private WeatherConverter weatherConverter;

    private CurrentWeatherUi currentWeatherUil;
    private List<DailyWeatherUi> dailyWeatherUi;
    private List<HourlyWeatherUi> hourlyWeatherUi;
    private WeatherCombiner weatherCombiner;
    private WeatherCombiner newWeather;

    @Before
    public void setUp() {
        weatherConverter = new WeatherConverter(RuntimeEnvironment.application.getApplicationContext());
        initCurrentWeather();
        initDailyWeather();
        initHourlyWEather();
        weatherCombiner = new WeatherCombiner(currentWeatherUil, hourlyWeatherUi, dailyWeatherUi);
    }

    @Test
    public void checkWindMsConverter() {
        weatherConverter.setWindValue(ConverterValues.WIND_SPEED_KMH);
        newWeather = weatherConverter.convert(weatherCombiner);
        CurrentWeatherUi newCurrentWeatherMs = newWeather.getCurrentWeather();
        Assert.assertEquals((int) (DEFAULT_WIND_VALUE * 3.6), (int) newCurrentWeatherMs.getWindSpeed());
    }

    @Test
    public void heckWindMphConverter() {
        weatherConverter.setWindValue(ConverterValues.WIND_SPEED_MILESH);
        newWeather = weatherConverter.convert(weatherCombiner);
        CurrentWeatherUi newCurrentWeatherMph = newWeather.getCurrentWeather();
        Assert.assertEquals((int) (DEFAULT_WIND_VALUE * 2.24), (int) newCurrentWeatherMph.getWindSpeed());
    }

    @Test
    public void checkTempCelsConverter() {
        weatherConverter.setTempValue("0");
        newWeather = weatherConverter.convert(weatherCombiner);
        CurrentWeatherUi newCurrentWeatherMph = newWeather.getCurrentWeather();
        Assert.assertEquals((int) (DEFAULT_TEMP_VALUE - 273), (int) newCurrentWeatherMph.getTemp());
    }

    @Test
    public void checkTempFarConverter() {
        weatherConverter.setTempValue("1");
        newWeather = weatherConverter.convert(weatherCombiner);
        CurrentWeatherUi newCurrentWeatherMph = newWeather.getCurrentWeather();
        Assert.assertEquals((int) (1.8 * (DEFAULT_TEMP_VALUE - 273) + 32), (int) newCurrentWeatherMph.getTemp());
    }

    @Test
    public void checkPressureToMm() {
        weatherConverter.setPressureValue(ConverterValues.PRESSURE_MM);
        newWeather = weatherConverter.convert(weatherCombiner);
        CurrentWeatherUi newCurrentWeatherMph = newWeather.getCurrentWeather();
        Assert.assertEquals((int) (DEFAULT_PRESSURE_VALUE * 0.75), (int) newCurrentWeatherMph.getPressure());
    }

    private void initCurrentWeather() {
        currentWeatherUil = new CurrentWeatherUi();
        currentWeatherUil.setWindSpeed(DEFAULT_WIND_VALUE);
        currentWeatherUil.setTemp(DEFAULT_TEMP_VALUE);
        currentWeatherUil.setPressure(DEFAULT_PRESSURE_VALUE);
    }

    private void initDailyWeather() {
        dailyWeatherUi = new ArrayList<>();
        DailyWeatherUi weather = new DailyWeatherUi();
        weather.setTempMax(DEFAULT_TEMP_VALUE);
        dailyWeatherUi.add(weather);
    }

    private void initHourlyWEather() {
        hourlyWeatherUi = new ArrayList<>();
        HourlyWeatherUi weather = new HourlyWeatherUi();
        weather.setTemp(DEFAULT_TEMP_VALUE);
        hourlyWeatherUi.add(weather);
    }
}
