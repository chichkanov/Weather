package dvinc.yamblzhomeproject.net;

import dvinc.yamblzhomeproject.repository.model.predictions.CityPrediction;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PredictionsApi {

    @GET("json?")
    Observable<CityPrediction> getPrediction(@Query("key") String key, @Query("types") String filterType, @Query("input") String input);

}
