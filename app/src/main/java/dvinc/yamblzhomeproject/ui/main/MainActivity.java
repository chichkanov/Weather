package dvinc.yamblzhomeproject.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.ui.selectCity.SelectCityActivity;
import timber.log.Timber;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final int REQUEST_CODE_SELECT_CITY = 0;

    private static final int MENU_ADDED_CITY_ID = 4;
    private static final int MENU_SETTINGS_ID = 0;
    private static final int MENU_ADD_CITY_ID = 1;
    private static final int MENU_EDIT_CITIES_ID = 2;
    private static final int MENU_ABOUT_ID = 3;

    private Drawer materialDrawer;

    @InjectPresenter
    public MainPresenter presenter;

    @Nullable
    @BindView(R.id.nav_tablet)
    FrameLayout frameLayoutTablets;

    private List<Integer> addedCitiesIds;

    @ProvidePresenter
    MainPresenter providePresenter() {
        return App.getComponent().getMainPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        presenter.setNavigationManager(this.getSupportFragmentManager());
        initDrawer(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = materialDrawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    private void initDrawer(Bundle savedInstState) {
        PrimaryDrawerItem addCityItem = getStandartPrimaryDrawerItem(R.drawable.ic_menu_add_city, getString(R.string.select_city_head), MENU_ADD_CITY_ID);
        PrimaryDrawerItem editCitiesItem = getStandartPrimaryDrawerItem(R.drawable.ic_menu_edit_cities, getString(R.string.edit_cities_head), MENU_EDIT_CITIES_ID);
        PrimaryDrawerItem settingsItem = getStandartPrimaryDrawerItem(R.drawable.ic_menu_settings, getString(R.string.nav_head_settings), MENU_SETTINGS_ID);
        PrimaryDrawerItem aboutItem = getStandartPrimaryDrawerItem(R.drawable.ic_menu_about, getString(R.string.nav_head_about), MENU_ABOUT_ID);

        SectionDrawerItem instrumentsSection = new SectionDrawerItem()
                .withName(R.string.nav_head_intsruments)
                .withDivider(false);

        DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withScrollToTopAfterClick(true)
                .addDrawerItems(instrumentsSection, addCityItem, editCitiesItem, settingsItem, aboutItem)
                .withDrawerWidthDp(260)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    switch ((int) drawerItem.getIdentifier()) {
                        case MENU_ADD_CITY_ID: {
                            presenter.openAddCityActivity(this, new Intent(this, SelectCityActivity.class), REQUEST_CODE_SELECT_CITY);
                            break;
                        }
                        case MENU_EDIT_CITIES_ID: {
                            presenter.openEditCitiesFragment();
                            break;
                        }
                        case MENU_SETTINGS_ID: {
                            presenter.openSettingsFragment();
                            break;
                        }
                        case MENU_ABOUT_ID: {
                            presenter.openAboutFragment();
                        }

                    }
                    return false;
                })
                .withSavedInstance(savedInstState);
        if (frameLayoutTablets != null) {
            materialDrawer = drawerBuilder.buildView();
            frameLayoutTablets.addView(materialDrawer.getSlider());
        } else {
            materialDrawer = drawerBuilder.build();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_CITY: {
                    presenter.getCities(true);
                    break;
                }
            }
        }
    }

    @Override
    public void initCitiesInMenu(List<CityEntity> cities, boolean fireOnClick) {
        Timber.d("Initializing menu");
        CityEntity activeItemTag = null;
        if (addedCitiesIds != null) {
            for (int i = 0; i < addedCitiesIds.size(); i++) {
                materialDrawer.removeItem(addedCitiesIds.get(i));
            }
            addedCitiesIds.clear();
        }

        addedCitiesIds = new ArrayList<>();

        for (int i = 0; i < cities.size(); i++) {
            Timber.d(cities.get(i).getCityTitle() + " " + cities.get(i).isActive());
            PrimaryDrawerItem newCity = getStandartPrimaryDrawerItem(R.drawable.ic_menu_location, cities.get(i).getCityTitle(), MENU_ADDED_CITY_ID + i)
                    .withTag(cities.get(i))
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
            materialDrawer.setSelection(activeItem, fireOnClick);
        }

        if (fireOnClick && cities.isEmpty()) {
            presenter.openAddCityActivity(this, new Intent(this, SelectCityActivity.class), REQUEST_CODE_SELECT_CITY);
        }
    }

    private PrimaryDrawerItem getStandartPrimaryDrawerItem(int iconId, String name, int id) {
        return new PrimaryDrawerItem()
                .withIcon(iconId)
                .withName(name)
                .withIdentifier(id)
                .withIconTintingEnabled(true);
    }
}
