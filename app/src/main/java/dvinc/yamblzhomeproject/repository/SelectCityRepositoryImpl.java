package dvinc.yamblzhomeproject.repository;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.net.PredictionsApi;
import dvinc.yamblzhomeproject.repository.model.predictions.CityPrediction;
import dvinc.yamblzhomeproject.repository.model.predictions.predictionInfo.PlaceInfoResponse;
import dvinc.yamblzhomeproject.utils.Constants;
import dvinc.yamblzhomeproject.utils.WeatherUtils;
import io.reactivex.Observable;

public class SelectCityRepositoryImpl implements SelectCityRepository {

    @Inject
    PredictionsApi predictionsApi;

    @Inject
    public SelectCityRepositoryImpl(){
    }

    @Override
    public Observable<CityPrediction> getPrediction(String cityName) {
        return predictionsApi.getPrediction(Constants.PLACES_API_KEY, WeatherUtils.getLocale(), cityName);
    }

    @Override
    public Observable<PlaceInfoResponse> getPredictionCoord(String cityId) {
        return predictionsApi.getPredictionCoord(Constants.PLACES_API_KEY, WeatherUtils.getLocale(), cityId);
    }
}
