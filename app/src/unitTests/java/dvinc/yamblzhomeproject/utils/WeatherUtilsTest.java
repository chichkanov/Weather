package dvinc.yamblzhomeproject.utils;

import org.junit.Test;

import dvinc.yamblzhomeproject.R;

import static junit.framework.Assert.assertEquals;

public class WeatherUtilsTest {

    @Test
    public void shouldReturnCloud() {
        assertEquals(R.string.wi_cloud, WeatherUtils.getMainIcon("03n"));
    }

    @Test
    public void shouldReturnCloudy() {
        assertEquals(R.string.wi_cloudy, WeatherUtils.getMainIcon("04n"));
    }

    @Test
    public void shouldReturnDayRain() {
        assertEquals(R.string.wi_day_rain, WeatherUtils.getMainIcon("10n"));
    }

    @Test
    public void shouldReturnDaySunny() {
        assertEquals(R.string.wi_day_sunny, WeatherUtils.getMainIcon("01n"));
    }

    @Test
    public void shouldReturnDaySunnyOvercast() {
        assertEquals(R.string.wi_day_sunny_overcast, WeatherUtils.getMainIcon("02d"));
    }

    @Test
    public void shouldReturnFog() {
        assertEquals(R.string.wi_fog, WeatherUtils.getMainIcon("50n"));
    }

    @Test
    public void shouldReturnRain() {
        assertEquals(R.string.wi_rain, WeatherUtils.getMainIcon("09n"));
    }

    @Test
    public void shouldReturnSnow() {
        assertEquals(R.string.wi_snow, WeatherUtils.getMainIcon("13n"));
    }

    @Test
    public void shouldReturnThunder() {
        assertEquals(R.string.wi_thunderstorm, WeatherUtils.getMainIcon("11n"));
    }

    @Test
    public void forecast_shouldReturnCloud() {
        assertEquals(R.drawable.wi_cloud, WeatherUtils.getForecastIcon("03n"));
    }

    @Test
    public void forecast_shouldReturnCloudy() {
        assertEquals(R.drawable.wi_cloudy, WeatherUtils.getForecastIcon("04n"));
    }

    @Test
    public void forecast_shouldReturnDayRain() {
        assertEquals(R.drawable.wi_day_rain, WeatherUtils.getForecastIcon("10n"));
    }

    @Test
    public void forecast_shouldReturnDaySunny() {
        assertEquals(R.drawable.wi_day_sunny, WeatherUtils.getForecastIcon("01n"));
    }

    @Test
    public void forecast_shouldReturnDaySunnyOvercast() {
        assertEquals(R.drawable.wi_day_sunny_overcast, WeatherUtils.getForecastIcon("02d"));
    }

    @Test
    public void forecast_shouldReturnFog() {
        assertEquals(R.drawable.wi_fog, WeatherUtils.getForecastIcon("50n"));
    }

    @Test
    public void forecast_shouldReturnRain() {
        assertEquals(R.drawable.wi_rain, WeatherUtils.getForecastIcon("09n"));
    }

    @Test
    public void forecast_shouldReturnSnow() {
        assertEquals(R.drawable.wi_snow, WeatherUtils.getForecastIcon("13n"));
    }

    @Test
    public void forecast_shouldReturnThunder() {
        assertEquals(R.drawable.wi_thunderstorm, WeatherUtils.getForecastIcon("11n"));
    }


}
