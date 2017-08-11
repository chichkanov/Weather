package dvinc.yamblzhomeproject.ui.navigation;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.arellomobile.mvp.MvpAppCompatActivity;

public class NavigationManager {

    private FragmentManager fragmentManager;
    private int containerId;

    public NavigationManager(FragmentManager fragmentManager, int containerId) {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    public void navigateTo(Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    public void navigateTo(MvpAppCompatActivity from, Intent intent, int requestCode){
        from.startActivityForResult(intent, requestCode);
    }

}
