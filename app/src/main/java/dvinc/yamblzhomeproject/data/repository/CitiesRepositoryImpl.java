package dvinc.yamblzhomeproject.data.repository;

import java.util.List;

import dvinc.yamblzhomeproject.db.AppDatabase;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CitiesRepositoryImpl implements CitiesRepository {

    private AppDatabase appDatabase;

    public CitiesRepositoryImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public Completable removeCity(CityEntity cityEntity) {
        return Completable.fromAction(() -> appDatabase.weatherDao().getWeatherForCityId(cityEntity.getCityId())
                .subscribeOn(Schedulers.io())
                .subscribe(weatherEntity -> {
                    appDatabase.weatherDao().deleteWeather(weatherEntity);
                    appDatabase.cityDao().deleteCity(cityEntity);
                }));
    }

    @Override
    public Flowable<List<CityEntity>> updateMenu() {
        return appDatabase.cityDao().getAllCities();
    }

    @Override
    public Single<List<CityEntity>> getMenuItems() {
        return Single.defer(() -> Single.just(appDatabase.cityDao().getAllCitiesSync()));
    }

    @Override
    public Completable setActiveCity(CityEntity cityEntity) {
        return Completable.fromAction(() -> {
            CityEntity prevActive = appDatabase.cityDao().getActiveCity();
            if (prevActive != null) {
                prevActive.setActive(false);
                Timber.d("Previous active city: %s", prevActive.getCityTitle());
                appDatabase.cityDao().updateCity(prevActive);
            }
            Timber.d("Current active city: %s", cityEntity.getCityTitle());
            cityEntity.setActive(true);
            appDatabase.cityDao().updateCity(cityEntity);
        });
    }
}
