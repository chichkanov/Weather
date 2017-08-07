package dvinc.yamblzhomeproject.repository;

import dvinc.yamblzhomeproject.repository.model.predictions.CityPrediction;
import dvinc.yamblzhomeproject.repository.model.predictions.predictionInfo.PlaceInfoResponse;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface SelectCityRepository {

    Observable<CityPrediction> getPrediction(String cityName);

    Single<PlaceInfoResponse> getPredictionCoord(String cityId);

    Completable saveCity(PlaceInfoResponse place, String name, String id);
}
