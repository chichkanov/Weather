package dvinc.yamblzhomeproject.data.uiModel.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dvinc.yamblzhomeproject.data.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.data.model.weather.dailyForecast.DailyList;
import dvinc.yamblzhomeproject.data.model.weather.dailyForecast.WeatherForecastDailyResponse;
import dvinc.yamblzhomeproject.data.model.weather.hourForecast.HourList;
import dvinc.yamblzhomeproject.data.model.weather.hourForecast.WeatherForecastHourlyResponse;
import dvinc.yamblzhomeproject.data.uiModel.CurrentWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.DailyWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.HourlyWeatherUi;

@Singleton
public class WeatherMapper {

    @Inject
    WeatherMapper() {

    }

    public CurrentWeatherUi transformCurrentWeather(WeatherResponse response) {
        CurrentWeatherUi currentWeatherUi = null;
        if (response != null) {
            currentWeatherUi = new CurrentWeatherUi();
            currentWeatherUi.setDescription(response.getWeatherCurrent().get(0).getDescription());
            currentWeatherUi.setHumidity(response.getMainCurrent().getHumidity());
            currentWeatherUi.setIcon(response.getWeatherCurrent().get(0).getIcon());
            currentWeatherUi.setPressure((int) response.getMainCurrent().getPressure());
            currentWeatherUi.setTemp((int) response.getMainCurrent().getTemp());
            currentWeatherUi.setTempMax((int) response.getMainCurrent().getTempMax());
            currentWeatherUi.setTempMin((int) response.getMainCurrent().getTempMin());
            currentWeatherUi.setWindSpeed((int) response.getWindCurrent().getSpeed());
        }
        return currentWeatherUi;
    }

    public List<HourlyWeatherUi> transformHourWeather(WeatherForecastHourlyResponse response) {
        List<HourlyWeatherUi> hourlyWeatherUi = null;
        if (response != null) {
            hourlyWeatherUi = new ArrayList<>();
            List<HourList> responseList = response.getList();
            for (int i = 0; i < responseList.size(); i++) {
                HourlyWeatherUi element = new HourlyWeatherUi();
                element.setTemp((int) responseList.get(i).getMainHourly().getTemp());
                element.setIcon(responseList.get(i).getWeatherHourly().get(0).getIcon());
                element.setDate(responseList.get(i).getDtHourly());
                hourlyWeatherUi.add(element);
            }

        }
        return hourlyWeatherUi;
    }

    public List<DailyWeatherUi> transformDailyWeather(WeatherForecastDailyResponse response) {
        List<DailyWeatherUi> dailyWeatherUi = null;
        if (response != null) {
            dailyWeatherUi = new ArrayList<>();
            List<DailyList> responseList = response.getListDaily();
            for (int i = 0; i < responseList.size(); i++) {
                DailyWeatherUi element = new DailyWeatherUi();
                element.setIcon(responseList.get(i).getWeatherDaily().get(0).getIcon());
                element.setTempMin((int) responseList.get(i).getTempDaily().getMin());
                element.setTempMax((int) responseList.get(i).getTempDaily().getMax());
                dailyWeatherUi.add(element);
            }

        }
        return dailyWeatherUi;
    }
}
