package dvinc.yamblzhomeproject.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import dvinc.yamblzhomeproject.repository.model.weather.core.Weather;
import dvinc.yamblzhomeproject.repository.model.weather.dailyForecast.DailyList;
import dvinc.yamblzhomeproject.repository.model.weather.hourForecast.HourList;

public class Converters {

    @TypeConverter
    public static List<DailyList> fromStringDaily(String value) {
        Type listType = new TypeToken<ArrayList<DailyList>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String objectToStringDaily(List<DailyList> response) {
        return new Gson().toJson(response);
    }

    @TypeConverter
    public static List<HourList> fromStringHourly(String value) {
        Type listType = new TypeToken<ArrayList<HourList>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String objectToStringHourly(List<HourList> response) {
        return new Gson().toJson(response);
    }

    @TypeConverter
    public static List<Weather> fromStringCurrent(String value) {
        Type listType = new TypeToken<ArrayList<Weather>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String objectToStringCurrent(List<Weather> response) {
        return new Gson().toJson(response);
    }

}
