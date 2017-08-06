package dvinc.yamblzhomeproject.ui.base;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.ui.about.AboutFragment;

interface MainView extends MvpView {

    void showFragment(Fragment fragment);

    void showAboutFragment(AboutFragment fragment);

    void initCitiesInMenu(List<CityEntity> cities);

    void showFragmentWithOverlay(Fragment fragment);
}
