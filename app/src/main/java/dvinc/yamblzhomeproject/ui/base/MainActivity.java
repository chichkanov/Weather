package dvinc.yamblzhomeproject.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final int REQUEST_CODE_SELECT_CITY = 0;

    private static final int MENU_ADDED_CITY_ID = 3;
    private static final int MENU_SETTINGS_ID = 0;
    private static final int MENU_ADD_CITY_ID = 1;
    private static final int MENU_EDIT_CITIES_ID = 2;

    private Drawer materialDrawer;
    private List<Integer> addedCitiesIds;

    @InjectPresenter
    public MainPresenter presenter;

    @Nullable
    @BindView(R.id.nav_tablet)
    FrameLayout frameLayoutTablets;

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
        presenter.setNavigationManager(this.getSupportFragmentManager());

        initDrawer(savedInstanceState);
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
                .withIconTintingEnabled(true)
                .withIdentifier(MENU_ADD_CITY_ID);

        PrimaryDrawerItem editCitiesItem = new PrimaryDrawerItem()
                .withName(R.string.edit_cities_head)
                .withIcon(R.drawable.ic_menu_edit_cities)
                .withIconTintingEnabled(true)
                .withIdentifier(MENU_EDIT_CITIES_ID);

        PrimaryDrawerItem settingsItem = new PrimaryDrawerItem()
                .withName(R.string.nav_head_settings)
                .withIcon(R.drawable.ic_menu_settings)
                .withIconTintingEnabled(true)
                .withIdentifier(MENU_SETTINGS_ID);

        SectionDrawerItem instrumentsSection = new SectionDrawerItem()
                .withName(R.string.nav_head_intsruments)
                .withDivider(false);

        DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(instrumentsSection, addCityItem, editCitiesItem, settingsItem)
                .withDrawerWidthDp(250)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    switch ((int) drawerItem.getIdentifier()) {
                        case MENU_ADD_CITY_ID: {
                            Intent intent = new Intent(this, SelectCityActivity.class);
                            startActivityForResult(intent, REQUEST_CODE_SELECT_CITY);
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
                    presenter.getCities();
                    break;
                }
            }
        }
    }

    @Override
    public void initCitiesInMenu(List<CityEntity> cities, boolean fireOnClick) {
        CityEntity activeItemTag = null;

        if (addedCitiesIds != null) {
            for (int i = 0; i < addedCitiesIds.size(); i++) {
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
                    .withIconTintingEnabled(true)
                    .withIdentifier(MENU_ADDED_CITY_ID + i)
                    .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                        presenter.openWeatherFragment((CityEntity) drawerItem.getTag());
                        return false;
                    });

            addedCitiesIds.add(MENU_ADDED_CITY_ID + i);
            materialDrawer.addItemAtPosition(newCity, 0);

            if (cities.get(i).isActive()) {
                activeItemTag = cities.get(i);
                if (fireOnClick) Log.e("ActiveItemInit", cities.get(i).getCityTitle());
                else Log.e("ActiveItemOBSERVER", cities.get(i).getCityTitle());
            }
        }

        if (activeItemTag != null) {
            IDrawerItem activeItem = materialDrawer.getDrawerItem(activeItemTag);
            materialDrawer.setSelection(activeItem, fireOnClick);
        }
    }

}
