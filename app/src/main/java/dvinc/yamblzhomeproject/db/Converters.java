package dvinc.yamblzhomeproject.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import dvinc.yamblzhomeproject.data.uiModel.DailyWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.HourlyWeatherUi;

public class Converters {

    @TypeConverter
    public static List<DailyWeatherUi> fromStringDaily(String value) {
        Type listType = new TypeToken<ArrayList<DailyWeatherUi>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String objectToStringDaily(List<DailyWeatherUi> response) {
        return new Gson().toJson(response);
    }

    @TypeConverter
    public static List<HourlyWeatherUi> fromStringHourly(String value) {
        Type listType = new TypeToken<ArrayList<HourlyWeatherUi>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String objectToStringHourly(List<HourlyWeatherUi> response) {
        return new Gson().toJson(response);
    }


}
