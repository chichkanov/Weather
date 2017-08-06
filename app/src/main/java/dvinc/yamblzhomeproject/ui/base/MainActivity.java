package dvinc.yamblzhomeproject.ui.base;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.ui.about.AboutFragment;

public class MainActivity extends MvpAppCompatActivity implements MainView, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private static final int ADDED_CITY_ID = 0;

    @InjectPresenter
    public MainPresenter presenter;

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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navigationView.getMenu().getItem(0).setChecked(true);
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
        }

        presenter.observeMenuChanges();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_weather) {
            presenter.openWeatherFragment(item.getTitle().toString());
        } else if (id == R.id.nav_settings) {
            presenter.openSettingsFragment();
        } else if (id == R.id.nav_about) {
            presenter.openAboutFragment();
        } else if (id == R.id.nav_select_city) {
            item.setCheckable(false);
            presenter.openSelectCityFragment();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        final Menu menu = navigationView.getMenu();
        menu.removeGroup(R.id.menu_group_cities);
        for (int i = 0; i < cities.size(); i++) {
            MenuItem item = menu.add(R.id.menu_group_cities, ADDED_CITY_ID, 0, cities.get(i).getCityTitle());
            item.setIcon(R.drawable.ic_menu_location);
            item.setCheckable(true);
        }
    }

}
