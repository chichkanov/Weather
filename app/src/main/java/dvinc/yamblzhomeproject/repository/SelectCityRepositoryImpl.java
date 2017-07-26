package dvinc.yamblzhomeproject.repository;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.net.PredictionsApi;
import dvinc.yamblzhomeproject.repository.model.predictions.CityPrediction;
import dvinc.yamblzhomeproject.utils.Constants;
import io.reactivex.Observable;

public class SelectCityRepositoryImpl implements SelectCityRepository {

    @Inject
    PredictionsApi predictionsApi;

    public SelectCityRepositoryImpl(){
        App.getComponent().inject(this);
    }

    @Override
    public Observable<CityPrediction> getPrediction(String cityName) {
        return predictionsApi.getPrediction(Constants.PLACES_API_KEY, "(cities)", cityName);
    }
}
