package dvinc.yamblzhomeproject.repository;

import dvinc.yamblzhomeproject.repository.model.predictions.CityPrediction;
import io.reactivex.Observable;

public interface SelectCityRepository {

    Observable<CityPrediction> getPrediction(String cityName);
}
