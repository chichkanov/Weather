package dvinc.yamblzhomeproject.net;

import dvinc.yamblzhomeproject.repository.model.predictions.CityPrediction;
import dvinc.yamblzhomeproject.repository.model.predictions.predictionInfo.PlaceInfoResponse;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PredictionsApi {

    @GET("autocomplete/json?types=(cities)")
    Observable<CityPrediction> getPrediction(@Query("key") String key,
                                             @Query("language") String lang,
                                             @Query("input") String input);

    @GET("details/json?types=(cities)")
    Single<PlaceInfoResponse> getPredictionCoord(@Query("key") String key,
                                                     @Query("language") String lang,
                                                     @Query("placeid") String placeId);

}
