package dvinc.yamblzhomeproject.ui.weather;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.data.model.predictions.predictionInfo.Location;
import dvinc.yamblzhomeproject.data.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.data.repository.CitiesRepositoryImpl;
import dvinc.yamblzhomeproject.data.repository.WeatherRepositoryImpl;
import dvinc.yamblzhomeproject.data.uiModel.CurrentWeatherUi;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.ui.BaseTestPresenter;
import dvinc.yamblzhomeproject.utils.converter.ConverterValues;
import dvinc.yamblzhomeproject.utils.converter.WeatherConverter;
import io.reactivex.Flowable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest extends BaseTestPresenter{

    @Mock
    WeatherRepositoryImpl weatherRepository;
    @Mock
    WeatherConverter weatherConverter;
    @Mock
    CitiesRepositoryImpl citiesRepository;

    @Mock
    WeatherView$$State view;

    @InjectMocks
    WeatherPresenter weatherPresenter;

    private CityEntity cityEntity;

    @Before
    public void setUp(){
        super.setUp();
        cityEntity = new CityEntity(new Location(12, 12), "id", "ke", true);
        weatherPresenter.setViewState(view);
    }

    @Test
    public void shouldShowWeatherWhenNetworkOkay(){
        WeatherCombiner weatherCombiner = new WeatherCombiner(new CurrentWeatherUi(), new ArrayList<>(), new ArrayList<>());
        weatherCombiner.setUpdatedTime(10000);

        when(weatherRepository.getWeatherData(cityEntity)).thenReturn(Flowable.just(weatherCombiner));
        weatherPresenter.getWeather(cityEntity);

        verify(view, times(1)).showLoading();
        verify(view, times(1)).updateLastUpdateTime(10000);
        verify(view, times(1)).updateWeatherCurrent(weatherCombiner.getCurrentWeather());
        verify(view, times(1)).updateWeatherDaily(weatherCombiner.getDayWeather());
        verify(view, times(1)).updateWeatherHourly(weatherCombiner.getHourWeather());
        verify(view, times(1)).hideLoading();
    }

    @Test
    public void shouldShowErrorWhenNetworkOkay(){
        when(weatherRepository.getWeatherData(cityEntity)).thenReturn(Flowable.error(new Throwable()));
        weatherPresenter.getWeather(cityEntity);

        verify(view, times(1)).showLoading();
        verify(view, times(1)).showError();
        verify(view, times(1)).showError();
    }

    @Test
    public void shouldReturnCorrectWindSpeedUnitTextForKm(){
        when(weatherConverter.getWindValue()).thenReturn(ConverterValues.WIND_SPEED_KMH);
        assertEquals(weatherPresenter.getWindSpeedUnitText(), R.string.weather_wind_speed_km);
    }

    @Test
    public void shouldReturnCorrectWindSpeedUnitTextForMiles(){
        when(weatherConverter.getWindValue()).thenReturn(ConverterValues.WIND_SPEED_MILESH);
        assertEquals(weatherPresenter.getWindSpeedUnitText(), R.string.weather_wind_speed_miles);
    }

    @Test
    public void shouldReturnCorrectWindSpeedUnitTextForMetres(){
        when(weatherConverter.getWindValue()).thenReturn("defaultValue");
        assertEquals(weatherPresenter.getWindSpeedUnitText(), R.string.weather_wind_speed_metr);
    }


    @Test
    public void shouldReturnCorrectPressureUnitTextForMmHg(){
        when(weatherConverter.getPressureValue()).thenReturn(ConverterValues.PRESSURE_MM);
        assertEquals(weatherPresenter.getPressureUnitText(), R.string.weather_pressure_mm);
    }

    @Test
    public void shouldReturnCorrectPressureUnitTextForHpa(){
        when(weatherConverter.getPressureValue()).thenReturn("defaultValue");
        assertEquals(weatherPresenter.getPressureUnitText(), R.string.weather_pressure_hpa);
    }

    @Test
    public void testFirstAttachAndGotErrorCity(){
        CityEntity cityEntity = new CityEntity(new Location(12, 12), "id", "ke", true);
        when(citiesRepository.getActiveCity()).thenReturn(Flowable.error(new Throwable()));
        weatherPresenter.onFirstViewAttach();
        verify(view).showError();
    }

}