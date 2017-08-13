package dvinc.yamblzhomeproject.ui.editCities;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.data.repository.CitiesRepository;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class EditCitiesPresenter extends MvpPresenter<EditCitiesView> {

    private CitiesRepository citiesRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    EditCitiesPresenter(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Disposable disposable = citiesRepository.getMenuItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> getViewState().showCitiesList(success),
                        error -> getViewState().showError());
        compositeDisposable.add(disposable);
    }

    void onRemoveClick(CityEntity cityEntity) {
        Disposable disposable = citiesRepository.removeCity(cityEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().onItemRemoved(cityEntity),
                        error -> getViewState().showError());
        compositeDisposable.add(disposable);
    }

    @Override
    public void destroyView(EditCitiesView view) {
        super.destroyView(view);
        compositeDisposable.clear();
    }
}
