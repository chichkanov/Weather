package dvinc.yamblzhomeproject.repository;

import dvinc.yamblzhomeproject.db.AppDatabase;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.net.PredictionsApi;
import dvinc.yamblzhomeproject.repository.model.predictions.CityPrediction;
import dvinc.yamblzhomeproject.repository.model.predictions.predictionInfo.Location;
import dvinc.yamblzhomeproject.repository.model.predictions.predictionInfo.PlaceInfoResponse;
import dvinc.yamblzhomeproject.utils.Constants;
import dvinc.yamblzhomeproject.utils.WeatherUtils;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class SelectCityRepositoryImpl implements SelectCityRepository {

    PredictionsApi predictionsApi;
    AppDatabase database;

    public SelectCityRepositoryImpl(PredictionsApi predictionsApi, AppDatabase appDatabase) {
        this.predictionsApi = predictionsApi;
        this.database = appDatabase;
    }

    @Override
    public Observable<CityPrediction> getPrediction(String cityName) {
        return predictionsApi.getPrediction(Constants.PLACES_API_KEY, WeatherUtils.getLocale(), cityName);
    }

    @Override
    public Single<PlaceInfoResponse> getPredictionCoord(String cityId) {
        return predictionsApi.getPredictionCoord(Constants.PLACES_API_KEY, WeatherUtils.getLocale(), cityId);
    }

    @Override
    public Completable saveCity(PlaceInfoResponse place, String name, String id) {
        Location location = new Location(place.getResult().getGeometry().getLocation().getLatitude(),
                place.getResult().getGeometry().getLocation().getLongitude());

        return Completable.fromAction(() -> {
            CityEntity entity = new CityEntity(location, id, name, true);
            CityEntity prevActive = database.cityDao().getActiveCity();
            if (prevActive != null) {
                prevActive.setActive(false);
                database.cityDao().updateCity(prevActive);
            }
            database.cityDao().insertNewCity(entity);
        });
    }
}
