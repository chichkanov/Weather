package dvinc.yamblzhomeproject.ui.selectCity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import dvinc.yamblzhomeproject.di.AppComponent;
import dvinc.yamblzhomeproject.di.TestComponent;
import dvinc.yamblzhomeproject.di.TestComponentRule;
import dvinc.yamblzhomeproject.repository.SelectCityRepositoryImpl;
import dvinc.yamblzhomeproject.repository.model.predictions.CityPrediction;
import dvinc.yamblzhomeproject.repository.model.predictions.Prediction;
import dvinc.yamblzhomeproject.repository.model.predictions.predictionInfo.PlaceInfoResponse;
import dvinc.yamblzhomeproject.utils.Settings;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class SelectCityPresenterTest {

    @Rule
    public TestComponentRule testComponentRule = new TestComponentRule(testAppComponent());

    @Mock
    private SelectCityRepositoryImpl repository;
    @Mock
    private Settings settings;
    @Mock
    private SelectCityView$$State selectCityViewState;

    private SelectCityPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new SelectCityPresenter();
        presenter.setViewState(selectCityViewState);

        RxAndroidPlugins.setMainThreadSchedulerHandler(v -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(v -> Schedulers.trampoline());
    }

    @Test
    public void shouldShowLoadAndShowDataWhenNetworkOn() {
        when(repository.getPrediction(anyString())).thenReturn(Observable.just(new CityPrediction()));
        presenter.setObservable(Observable.just("Any city"));

        verify(repository).getPrediction(anyString());
        verify(selectCityViewState).showList(any());
        verify(selectCityViewState, never()).showError();
    }

    @Test
    public void shouldShowShowErrorWhenSomethingWrong() {
        when(repository.getPrediction(anyString())).thenReturn(Observable.error(new Throwable()));
        presenter.setObservable(Observable.just("Any city"));

        verify(repository).getPrediction(anyString());
        verify(selectCityViewState).showError();
    }

    @Test
    public void shouldAskForPlaceInfo() {
        Observable<PlaceInfoResponse> info = Observable.just(mock(PlaceInfoResponse.class));

        when(repository.getPredictionCoord(any())).thenReturn(info);
        presenter.citySelected(new Prediction());

        verify(repository).getPredictionCoord(any());
    }

    @Test
    public void shouldNotCrashWhenViewDetached(){
        when(repository.getPrediction(anyString())).thenReturn(Observable.error(new Throwable()));
        presenter.setObservable(Observable.just("Any city"));

        presenter.detachView(selectCityViewState);
        presenter.destroyView(selectCityViewState);

        verify(selectCityViewState).showError();
    }

    private AppComponent testAppComponent() {
        return new TestComponent() {
            @Override
            public void inject(SelectCityPresenter selectCityPresenter) {
                selectCityPresenter.settings = settings;
                selectCityPresenter.repository = repository;
            }
        };
    }

}