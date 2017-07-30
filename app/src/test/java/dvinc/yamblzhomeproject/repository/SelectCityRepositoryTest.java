package dvinc.yamblzhomeproject.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dvinc.yamblzhomeproject.net.PredictionsApi;
import dvinc.yamblzhomeproject.repository.model.predictions.CityPrediction;
import dvinc.yamblzhomeproject.repository.model.predictions.predictionInfo.PlaceInfoResponse;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SelectCityRepositoryTest {

    @Mock
    PredictionsApi predictionsApi;

    @InjectMocks
    SelectCityRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void prediction_shouldReturnDataWhenValid() {
        when(predictionsApi.getPrediction(anyString(), anyString(), anyString())).thenReturn(Observable.just(mock(CityPrediction.class)));

        TestObserver<CityPrediction> observer = TestObserver.create();
        repository.getPrediction("Test").subscribe(observer);

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertValueCount(1);
    }

    @Test
    public void prediction_shouldReturnErrorDataWhenNotValid() {
        Throwable error = new Throwable();
        when(predictionsApi.getPrediction(any(), any(), any())).thenReturn(Observable.error(error));

        TestObserver<CityPrediction> observer = TestObserver.create();
        repository.getPrediction(null).subscribe(observer);

        observer.assertSubscribed();
        observer.assertError(error);
    }

    @Test
    public void coord_shouldReturnDataWhenValid() {
        when(predictionsApi.getPredictionCoord(anyString(), anyString(), anyString())).thenReturn(Observable.just(mock(PlaceInfoResponse.class)));

        TestObserver<PlaceInfoResponse> observer = TestObserver.create();
        repository.getPredictionCoord("Test").subscribe(observer);

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertValueCount(1);
    }

    @Test
    public void coord_shouldReturnErrorDataWhenNotValid() {
        Throwable error = new Throwable();
        when(predictionsApi.getPredictionCoord(any(), any(), any())).thenReturn(Observable.error(error));

        TestObserver<PlaceInfoResponse> observer = TestObserver.create();
        repository.getPredictionCoord(null).subscribe(observer);

        observer.assertSubscribed();
        observer.assertError(error);
    }
}
