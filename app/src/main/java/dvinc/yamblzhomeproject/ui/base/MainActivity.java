package dvinc.yamblzhomeproject.ui.base;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
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

    private static final int MENU_ADDED_CITY_ID = 2;
    private static final int MENU_SETTINGS_ID = 0;
    private static final int MENU_ADD_CITY_ID = 1;

    private Drawer materialDrawer;
    private List<Integer> addedCitiesIds;

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

        initDrawer(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCities();
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
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
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

                    }
                    return false;
                })
                .withSavedInstance(savedInstState)
                .build();
    }

    @Override
    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    public void showAboutFragment(AboutFragment fragment) {
        fragment.show(getSupportFragmentManager(), "Tag");
    }

    @Override
    public void initCitiesInMenu(List<CityEntity> cities) {
        CityEntity activeItemTag = null;

        if(addedCitiesIds != null){
            for(int i = 0; i < addedCitiesIds.size(); i++){
                materialDrawer.removeItem(addedCitiesIds.get(i));
            }
            addedCitiesIds.clear();
        }

        addedCitiesIds = new ArrayList<>();

        for (int i = 0; i < cities.size(); i++) {
            PrimaryDrawerItem newCity = new PrimaryDrawerItem()
                    .withIcon(R.drawable.ic_menu_location)
                    .withName(cities.get(i).getCityTitle())
                    .withTag(cities.get(i))
                    .withIdentifier(MENU_ADDED_CITY_ID + i)
                    .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                        presenter.openWeatherFragment((CityEntity) drawerItem.getTag());
                        return false;
                    });

            addedCitiesIds.add(MENU_ADDED_CITY_ID + i);
            materialDrawer.addItemAtPosition(newCity, 0);

            if (cities.get(i).isActive()) {
                activeItemTag = cities.get(i);
            }
        }

        if (activeItemTag != null) {
            IDrawerItem activeItem = materialDrawer.getDrawerItem(activeItemTag);
            materialDrawer.setSelection(activeItem);
        }

    }

}
