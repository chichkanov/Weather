package dvinc.yamblzhomeproject.data;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dvinc.yamblzhomeproject.data.model.predictions.CityPrediction;
import dvinc.yamblzhomeproject.data.model.predictions.predictionInfo.PlaceInfoResponse;
import dvinc.yamblzhomeproject.data.repository.SelectCityRepositoryImpl;
import dvinc.yamblzhomeproject.db.AppDatabase;
import dvinc.yamblzhomeproject.db.dao.CityDao;
import dvinc.yamblzhomeproject.net.PredictionsApi;
import dvinc.yamblzhomeproject.utils.FileLoader;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SelectCityRepositoryTest {

    private static final String CITY_INPUT = "Moscow";
    private static final String CITY_ID = "ChIJybDUc_xKtUYRTM9XV8zWRD0";

    @Mock
    AppDatabase appDatabase;
    @Mock
    PredictionsApi predictionsApi;

    @InjectMocks
    SelectCityRepositoryImpl selectCityRepository;

    private FileLoader fileLoader = new FileLoader();
    CityPrediction prediction;
    PlaceInfoResponse predictionInfo;

    @Before
    public void setUp() {
        prediction = fileLoader.loadTestData("CityPrediction.json", CityPrediction.class);
        predictionInfo = fileLoader.loadTestData("PlaceInfoResponse.json", PlaceInfoResponse.class);

        when(predictionsApi.getPrediction(anyString(), anyString(), anyString())).thenReturn(Observable.just(prediction));
        when(predictionsApi.getPredictionCoord(anyString(), anyString(), anyString())).thenReturn(Single.just(predictionInfo));
        when(appDatabase.cityDao()).thenReturn(mock(CityDao.class));
    }

    @Test
    public void shouldGetPredictionForInput() {
        TestObserver<CityPrediction> testObserver = selectCityRepository.getPrediction(CITY_INPUT).test();
        testObserver.assertSubscribed()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValue(prediction);
    }

    @Test
    public void shouldGetCoordsForInput() {
        TestObserver<PlaceInfoResponse> testObserver = selectCityRepository.getPredictionCoord(CITY_ID).test();
        testObserver.assertSubscribed()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValue(predictionInfo);
    }

    @Test
    public void shouldSaveNewCity() {
        PlaceInfoResponse predictionInfo = fileLoader.loadTestData("PlaceInfoResponse.json", PlaceInfoResponse.class);

        predictionInfo.getStatus();

        TestObserver<Void> testSubscriber = selectCityRepository.saveCity(predictionInfo, CITY_INPUT, CITY_ID).test();
        testSubscriber.assertSubscribed()
                .assertNoErrors();
    }

}
