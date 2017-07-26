package dvinc.yamblzhomeproject.net;

import dvinc.yamblzhomeproject.repository.model.predictions.CityPrediction;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PredictionsApi {

    @GET("json?types=(cities)")
    Observable<CityPrediction> getPrediction(@Query("key") String key,
                                             @Query("language") String lang,
                                             @Query("input") String input);

}
