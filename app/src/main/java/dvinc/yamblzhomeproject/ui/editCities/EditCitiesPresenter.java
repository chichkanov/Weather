package dvinc.yamblzhomeproject.ui.editCities;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.data.repository.CitiesRepository;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class EditCitiesPresenter extends MvpPresenter<EditCitiesView> {

    private CitiesRepository citiesRepository;
    private Disposable citiesListSubscription;
    private Disposable removeCitySubscription;

    @Inject
    EditCitiesPresenter(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        citiesListSubscription = citiesRepository.getMenuItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> getViewState().showCitiesList(success),
                        error -> getViewState().showError());
    }

    void onRemoveClick(CityEntity cityEntity) {
        removeCitySubscription = citiesRepository.removeCity(cityEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().onItemRemoved(cityEntity),
                        error -> getViewState().showError());
    }

    @Override
    public void destroyView(EditCitiesView view) {
        super.destroyView(view);
        if (citiesListSubscription != null) {
            citiesListSubscription.dispose();
        }
        if (removeCitySubscription != null) {
            removeCitySubscription.dispose();
        }
    }
}
