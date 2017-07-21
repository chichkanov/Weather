package dvinc.yamblzhomeproject.ui.weather;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import dvinc.yamblzhomeproject.repository.model.WeatherResponse;

public interface WeatherView {

    void updateWeatherParameters(WeatherResponse weatherData);

    void showError(String string);

}
