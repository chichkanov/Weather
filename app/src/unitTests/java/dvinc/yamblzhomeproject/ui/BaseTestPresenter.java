package dvinc.yamblzhomeproject.ui;

import org.junit.Before;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class BaseTestPresenter {

    @Before
    public void setUp() {
        RxAndroidPlugins.setMainThreadSchedulerHandler(v -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(v -> Schedulers.trampoline());
    }
}
