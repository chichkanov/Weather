package dvinc.yamblzhomeproject.di.modules;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dvinc.yamblzhomeproject.net.PredictionsApi;
import dvinc.yamblzhomeproject.net.WeatherApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofit() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
    }

    @Provides
    @Singleton
    @Named("retrofitWeather")
    Retrofit provideRetrofitWeather(Retrofit.Builder builder) {
        return builder.baseUrl("http://api.openweathermap.org/data/2.5/")
                .build();
    }

    @Provides
    @Singleton
    @Named("retrofitPrediction")
    Retrofit provideRetrofitPrediction(Retrofit.Builder builder) {
        return builder.baseUrl("https://maps.googleapis.com/maps/api/place/")
                .build();
    }

    @Provides
    @Singleton
    WeatherApi providesWeatherApi(@Named("retrofitWeather") Retrofit retrofit) {
        return retrofit.create(WeatherApi.class);
    }

    @Provides
    @Singleton
    PredictionsApi providesPredictionsApi(@Named("retrofitPrediction") Retrofit retrofit) {
        return retrofit.create(PredictionsApi.class);
    }
}
