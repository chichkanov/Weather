package dvinc.yamblzhomeproject.repository;

import dvinc.yamblzhomeproject.repository.model.predictions.CityPrediction;
import dvinc.yamblzhomeproject.repository.model.predictions.predictionInfo.PlaceInfoResponse;
import io.reactivex.Observable;

public interface SelectCityRepository {

    Observable<CityPrediction> getPrediction(String cityName);

    Observable<PlaceInfoResponse> getPredictionCoord(String cityId);
}
