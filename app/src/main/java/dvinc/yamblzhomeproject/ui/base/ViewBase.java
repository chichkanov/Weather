package dvinc.yamblzhomeproject.ui.base;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.support.v4.app.Fragment;

import dvinc.yamblzhomeproject.ui.about.AboutFragment;

public interface ViewBase {

    void showFragment(Fragment fragment);

    void showAboutFragment(AboutFragment fragment);
}
