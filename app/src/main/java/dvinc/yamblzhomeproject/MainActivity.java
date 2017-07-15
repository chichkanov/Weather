package dvinc.yamblzhomeproject;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dvinc.yamblzhomeproject.fragments.AboutFragment;
import dvinc.yamblzhomeproject.fragments.SettingsFragment;
import dvinc.yamblzhomeproject.fragments.WeatherFragment;

/**
 * TODO: Описание класса
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.activity_main_title);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Дубликат, для теста
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().
                beginTransaction();
        WeatherFragment myFragment = new WeatherFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, myFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_weather){

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().
                    beginTransaction();
            WeatherFragment myFragment = new WeatherFragment();
            fragmentTransaction.replace(R.id.fragmentContainer, myFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_settings) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().
                    beginTransaction();
            SettingsFragment myFragment = new SettingsFragment();
            fragmentTransaction.replace(R.id.fragmentContainer, myFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_about) {

            AboutFragment aboutFragment = new AboutFragment();
            aboutFragment.show(getSupportFragmentManager(), "tag");

            //showAboutDialog();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    /**
//     * Диалог "О Приложении". Показывает диалог с описанием приложения.
//     */
//    private void showAboutDialog(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(R.string.about_dialog_head);
//        builder.setMessage(R.string.about_dialog_decs);
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
}
