package dvinc.yamblzhomeproject.ui.weather;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.data.repository.CitiesRepository;
import dvinc.yamblzhomeproject.data.repository.WeatherRepository;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.utils.converter.ConverterValues;
import dvinc.yamblzhomeproject.utils.converter.WeatherConverter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    private WeatherRepository repository;
    private WeatherConverter weatherConverter;
    private CitiesRepository citiesRepository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public WeatherPresenter(WeatherRepository repository, CitiesRepository citiesRepository, WeatherConverter weatherConverter) {
        this.repository = repository;
        this.weatherConverter = weatherConverter;
        this.citiesRepository = citiesRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        Disposable disposable = citiesRepository.getActiveCity()
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> {
                    Timber.e("Active is %s", next.getCityTitle());
                    getWeather(next);
                }, error -> getViewState().showError());

        compositeDisposable.add(disposable);
    }

    void getWeather(CityEntity cityEntity) {
        Timber.d("Start loading weather");
        getViewState().showLoading();
        Disposable disposable = repository.getWeatherData(cityEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(next -> {
                            Timber.d("Weather Loaded");
                            getViewState().updateWeatherCurrent(next.getCurrentWeather());
                            getViewState().updateWeatherHourly(next.getHourWeather());
                            getViewState().updateWeatherDaily(next.getDayWeather());
                            getViewState().updateLastUpdateTime(next.getUpdatedTime());
                            getViewState().showCityName(cityEntity.getCityTitle());
                        },
                        error -> {
                            getViewState().hideLoading();
                            Timber.e("Weather error");
                            getViewState().showError();
                        }, () -> getViewState().hideLoading());

        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    int getWindSpeedUnitText() {
        switch (weatherConverter.getWindValue()) {
            case ConverterValues.WIND_SPEED_KMH: {
                return R.string.weather_wind_speed_km;
            }
            case ConverterValues.WIND_SPEED_MILESH: {
                return R.string.weather_wind_speed_miles;
            }
        }
        return R.string.weather_wind_speed_metr;
    }

    int getPressureUnitText() {
        if (weatherConverter.getPressureValue().equals(ConverterValues.PRESSURE_MM)) {
            return R.string.weather_pressure_mm;
        }
        return R.string.weather_pressure_hpa;
    }

}
