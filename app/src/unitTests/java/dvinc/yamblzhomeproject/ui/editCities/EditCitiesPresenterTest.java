package dvinc.yamblzhomeproject.ui.editCities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import dvinc.yamblzhomeproject.data.repository.CitiesRepositoryImpl;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.ui.BaseTestPresenter;
import io.reactivex.Completable;
import io.reactivex.Single;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EditCitiesPresenterTest extends BaseTestPresenter {

    @Mock
    CitiesRepositoryImpl citiesRepository;

    @Mock
    EditCitiesView$$State view;

    @InjectMocks
    EditCitiesPresenter presenter;

    @Before
    public void setUp() {
        super.setUp();
        presenter.setViewState(view);
    }

    @Test
    public void shouldShowListOfCitiesOnFirstAttach() {
        List<CityEntity> list = new ArrayList<>();
        when(citiesRepository.getMenuItems()).thenReturn(Single.just(list));
        presenter.onFirstViewAttach();
        verify(view, times(1)).showCitiesList(list);
    }

    @Test
    public void shouldShowErrorWhenIncorrectMenuItemsOnFirstStart() {
        when(citiesRepository.getMenuItems()).thenReturn(Single.error(new Throwable()));
        presenter.onFirstViewAttach();
        verify(view, times(1)).showError();
    }

    @Test
    public void shouldRemoveItemOnClick() {
        CityEntity cityEntity = new CityEntity();
        when(citiesRepository.removeCity(cityEntity)).thenReturn(Completable.complete());
        presenter.onRemoveClick(cityEntity);
        verify(view, times(1)).onItemRemoved(cityEntity);
    }

    @Test
    public void shouldShowErrorIfDeleteUnsuccesfull() {
        CityEntity cityEntity = new CityEntity();
        when(citiesRepository.removeCity(cityEntity)).thenReturn(Completable.error(new Throwable()));
        presenter.onRemoveClick(cityEntity);
        verify(view, times(1)).showError();
    }
}
