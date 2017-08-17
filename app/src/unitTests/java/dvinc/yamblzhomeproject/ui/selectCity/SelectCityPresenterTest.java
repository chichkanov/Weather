package dvinc.yamblzhomeproject.ui.selectCity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dvinc.yamblzhomeproject.data.model.predictions.CityPrediction;
import dvinc.yamblzhomeproject.data.model.predictions.predictionInfo.PlaceInfoResponse;
import dvinc.yamblzhomeproject.data.repository.SelectCityRepositoryImpl;
import dvinc.yamblzhomeproject.ui.BaseTestPresenter;
import dvinc.yamblzhomeproject.utils.FileLoader;
import io.reactivex.Observable;
import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SelectCityPresenterTest extends BaseTestPresenter {

    @Mock
    SelectCityRepositoryImpl selectCityRepository;

    @Mock
    SelectCityView$$State view;

    @InjectMocks
    SelectCityPresenter presenter;

    private FileLoader fileLoader = new FileLoader();

    @Before
    public void setUp() {
        super.setUp();
        presenter.setViewState(view);
    }

    @Test
    public void shouldSetObservaableCorrect() {
        CityPrediction cityPrediction = fileLoader.loadTestData("CityPrediction.json", CityPrediction.class);
        when(selectCityRepository.getPrediction(anyString())).thenReturn(Observable.just(cityPrediction));
        presenter.setObservable(Observable.just("Items"));
        verify(view, times(1)).showList(cityPrediction.getPredictions());
    }

    @Test
    public void shouldShowErrorWhenObservaableInCorrect() {
        when(selectCityRepository.getPrediction(anyString())).thenReturn(Observable.error(new Throwable()));
        presenter.setObservable(Observable.just("Items"));
        verify(view).showError();
    }

    @Test
    public void checkCitySelected() {
        PlaceInfoResponse placeInfoResponse = fileLoader.loadTestData("PlaceInfoResponse.json", PlaceInfoResponse.class);
        CityPrediction cityPrediction = fileLoader.loadTestData("CityPrediction.json", CityPrediction.class);
        when(selectCityRepository.getPredictionCoord("ChIJybDUc_xKtUYRTM9XV8zWRD0")).thenReturn(Single.just(placeInfoResponse));
        presenter.citySelected(cityPrediction.getPredictions().get(0));
    }

    @Test
    public void checkClearButtonClear() {
        presenter.clearButtonCLicked("Text");
        verify(view, only()).clearText();
    }

    @Test
    public void checkClearButtonSwitchScreen() {
        presenter.clearButtonCLicked("");
        verify(view, only()).goToWeather();
    }

}