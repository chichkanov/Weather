package dvinc.yamblzhomeproject.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import dvinc.yamblzhomeproject.data.model.predictions.predictionInfo.Location;
import dvinc.yamblzhomeproject.data.repository.CitiesRepositoryImpl;
import dvinc.yamblzhomeproject.db.AppDatabase;
import dvinc.yamblzhomeproject.db.dao.CityDao;
import dvinc.yamblzhomeproject.db.dao.WeatherDao;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.db.entities.WeatherEntity;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CitiesRepositoryTest {

    @Mock
    AppDatabase appDatabase;

    @InjectMocks
    CitiesRepositoryImpl citiesRepository;

    private CityEntity cityEntity;

    @Before
    public void setUp() {
        cityEntity = new CityEntity(new Location(12.3, 12.3), "id", "title", true);
        when(appDatabase.cityDao()).thenReturn(mock(CityDao.class));
        when(appDatabase.weatherDao()).thenReturn(mock(WeatherDao.class));
    }

    @Test
    public void shouldGetActiveCity() {
        when(appDatabase.cityDao().getActiveCityFlowable()).thenReturn(Flowable.just(cityEntity));

        TestSubscriber<CityEntity> testSubscriber = citiesRepository.getActiveCity().test();
        testSubscriber
                .assertSubscribed()
                .assertNoErrors()
                .assertValue(cityEntity);

    }

    @Test
    public void shouldUpdateMenu() {
        List<CityEntity> list = new ArrayList<>();
        list.add(cityEntity);
        when(appDatabase.cityDao().getAllCities()).thenReturn(Flowable.just(list));

        TestSubscriber<List<CityEntity>> testSubscriber = citiesRepository.updateMenu().test();
        testSubscriber
                .assertSubscribed()
                .assertNoErrors()
                .assertValue(list);
    }

    @Test
    public void shouldGetMenuItemsMenu() {
        List<CityEntity> list = new ArrayList<>();
        list.add(cityEntity);
        when(appDatabase.cityDao().getAllCitiesSync()).thenReturn(list);

        TestObserver<List<CityEntity>> testObserver = citiesRepository.getMenuItems().test();
        testObserver
                .assertSubscribed()
                .assertNoErrors()
                .assertValue(list);
    }

    @Test
    public void shouldSetActiveCity() {
        CityEntity newCity = new CityEntity(new Location(12, 13), "id", "KekCity", false);
        when(appDatabase.cityDao().getActiveCity()).thenReturn(cityEntity);
        TestObserver<Void> testObserver = citiesRepository.setActiveCity(newCity).test();
        testObserver.assertSubscribed()
                .assertNoErrors();
        Assert.assertEquals(true, newCity.isActive());
    }

    @Test
    public void shouldRemoveCity() {
        WeatherEntity weatherEntity = new WeatherEntity();
        when(appDatabase.weatherDao().getWeatherForCityId(anyString())).thenReturn(Maybe.just(weatherEntity));

        TestObserver<Void> testObserver = citiesRepository.removeCity(cityEntity).test();
        testObserver.assertSubscribed()
                .assertNoErrors();
    }

}
