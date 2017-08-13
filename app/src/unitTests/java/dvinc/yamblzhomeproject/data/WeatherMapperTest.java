package dvinc.yamblzhomeproject.data;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import dvinc.yamblzhomeproject.data.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.data.model.weather.dailyForecast.WeatherForecastDailyResponse;
import dvinc.yamblzhomeproject.data.model.weather.hourForecast.WeatherForecastHourlyResponse;
import dvinc.yamblzhomeproject.data.uiModel.CurrentWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.DailyWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.HourlyWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.mapper.WeatherMapper;
import dvinc.yamblzhomeproject.utils.FileLoader;

import static junit.framework.Assert.assertEquals;

public class WeatherMapperTest {

    private WeatherMapper weatherMapper;

    private FileLoader fileLoader = new FileLoader();

    @Before
    public void setUp() {
        weatherMapper = new WeatherMapper();
    }

    @Test
    public void testTransformCurrentWeather() {
        WeatherResponse response = fileLoader.loadTestData("WeatherResponse.json", WeatherResponse.class);
        CurrentWeatherUi weather = weatherMapper.transformCurrentWeather(response);

        assertEquals("light intensity drizzle", weather.getDescription());
        assertEquals(81, weather.getHumidity());
        assertEquals("09d", weather.getIcon());
        assertEquals(1012, (int) weather.getPressure());
        assertEquals(280, (int) weather.getTemp());
        assertEquals(281, (int) weather.getTempMax());
        assertEquals(279, (int) weather.getTempMin());
        assertEquals(4, (int) weather.getWindSpeed());
    }

    @Test
    public void testTransformDayWeather() {
        WeatherForecastDailyResponse item = fileLoader.loadTestData("WeatherForecastDailyResponse.json", WeatherForecastDailyResponse.class);
        List<DailyWeatherUi> list = weatherMapper.transformDailyWeather(item);

        DailyWeatherUi weather = list.get(0);
        assertEquals("01d", weather.getIcon());
        assertEquals(27, (int) weather.getTempMax());
        assertEquals(22, (int) weather.getTempMin());

    }

    @Test
    public void testTransformHourWeather() {
        WeatherForecastHourlyResponse item = fileLoader.loadTestData("WeatherForecastHourlyResponse.json", WeatherForecastHourlyResponse.class);
        List<HourlyWeatherUi> list = weatherMapper.transformHourWeather(item);

        HourlyWeatherUi weather = list.get(0);
        assertEquals("02n", weather.getIcon());
        assertEquals(261, (int) weather.getTemp());
        assertEquals(1485799200, (int) weather.getDate());

    }
}
