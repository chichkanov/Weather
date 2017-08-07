package dvinc.yamblzhomeproject.ui.base;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.ui.about.AboutFragment;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private static final int MENU_ADDED_CITY_ID = 0;
    private static final int MENU_SETTINGS_ID = 1;
    private static final int MENU_ADD_CITY_ID = 2;

    private Drawer materialDrawer;

    @InjectPresenter
    public MainPresenter presenter;

    private int previousCitiesCount;

    @ProvidePresenter
    MainPresenter providePresenter() {
        return App.getComponent().getMainPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initDrawer(savedInstanceState);

        if (savedInstanceState == null) {
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = materialDrawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    private void initDrawer(Bundle savedInstState) {
        PrimaryDrawerItem addCityItem = new PrimaryDrawerItem()
                .withName(R.string.select_city_head)
                .withIcon(R.drawable.ic_menu_add_city)
                .withIdentifier(MENU_ADD_CITY_ID);

        PrimaryDrawerItem settingsItem = new PrimaryDrawerItem()
                .withName(R.string.nav_head_settings)
                .withIcon(R.drawable.ic_menu_settings)
                .withIdentifier(MENU_SETTINGS_ID);


        materialDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(addCityItem, settingsItem)
                .withDrawerWidthDp(250)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    switch ((int) drawerItem.getIdentifier()) {
                        case MENU_ADD_CITY_ID: {
                            presenter.openSelectCityFragment();
                            break;
                        }
                        case MENU_SETTINGS_ID: {
                            presenter.openSettingsFragment();
                            break;
                        }
                        case MENU_ADDED_CITY_ID: {
                            Log.i("Added city selected", String.valueOf(drawerItem.getTag()));
                            presenter.openWeatherFragment((CityEntity) drawerItem.getTag());
                            break;
                        }
                    }
                    return false;
                })
                .withSavedInstance(savedInstState)
                .build();
    }

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    public void showFragmentWithOverlay(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, fragment, fragment.getClass().getName())
                .commit();
    }

    public void showAboutFragment(AboutFragment fragment) {
        fragment.show(getSupportFragmentManager(), "Tag");
    }

    @Override
    public void initCitiesInMenu(List<CityEntity> cities) {
        for(int i = 0; i < cities.size(); i++){
            materialDrawer.removeItem(MENU_ADDED_CITY_ID);
        }

        for (int i = 0; i < cities.size(); i++) {
            PrimaryDrawerItem newCity = new PrimaryDrawerItem()
                    .withIcon(R.drawable.ic_menu_location)
                    .withName(cities.get(i).getCityTitle())
                    .withTag(cities.get(i))
                    .withIdentifier(MENU_ADDED_CITY_ID);

            materialDrawer.addItemAtPosition(newCity, 0);
        }

    }

}
