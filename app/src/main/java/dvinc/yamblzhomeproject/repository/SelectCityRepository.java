package dvinc.yamblzhomeproject.repository;

import dvinc.yamblzhomeproject.repository.model.predictions.CityPrediction;
import io.reactivex.Observable;

interface SelectCityRepository {

    Observable<CityPrediction> getPrediction(String cityName);
}
