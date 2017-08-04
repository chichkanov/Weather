package dvinc.yamblzhomeproject.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dvinc.yamblzhomeproject.db.AppDatabase;
import dvinc.yamblzhomeproject.utils.Settings;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    Settings provideSettings(Context context) {
        return new Settings(context);
    }

    @Provides
    @Singleton
    AppDatabase provideDatabase(Context context){
        return Room.databaseBuilder(context, AppDatabase.class, "cityWeather").build();
    }
}
