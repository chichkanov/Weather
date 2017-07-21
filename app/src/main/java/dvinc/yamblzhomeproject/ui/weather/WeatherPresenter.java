package dvinc.yamblzhomeproject.ui.weather;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import android.content.Context;

public interface WeatherPresenter<T extends WeatherView> {

    void attachView(T weatherView);

    void detachView();

    void getWeatherFromInternet(Context context);

    void getWeatherDataFromCache(Context context);
}
