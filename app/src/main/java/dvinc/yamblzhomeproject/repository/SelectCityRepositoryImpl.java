package dvinc.yamblzhomeproject.repository;

import dvinc.yamblzhomeproject.repository.model.predictions.CityPrediction;
import io.reactivex.Observable;

public class SelectCityRepositoryImpl implements SelectCityRepository {

    @Override
    public Observable<CityPrediction> getPrediction(String cityName) {
        return null;
    }
}
