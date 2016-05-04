package io.github.pablomusumeci.find.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import io.github.pablomusumeci.find.R;
import io.github.pablomusumeci.find.domain.events.ErrorEvent;
import io.github.pablomusumeci.find.domain.model.scanning.TrackingStrategy;
import io.github.pablomusumeci.find.domain.services.background.ScanningService;
import io.github.pablomusumeci.find.ui.fragments.LearningFragment;
import io.github.pablomusumeci.find.ui.fragments.MainFragment;
import io.github.pablomusumeci.find.ui.fragments.TrackingFragment;
import io.github.pablomusumeci.find.ui.navigationDrawer.DrawerAdapter;
import io.github.pablomusumeci.find.ui.navigationDrawer.DrawerFragmentsBuilder;
import io.github.pablomusumeci.find.ui.navigationDrawer.DrawerItem;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements MainActivityListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private DrawerAdapter drawerAdapter;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        goToMainFragment();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        drawerAdapter = new DrawerAdapter(this);
        mDrawerList.setAdapter(drawerAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mTitle = mDrawerTitle = getTitle();
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                setActionBarTitle(mTitle);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setActionBarTitle(mDrawerTitle);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showErrorDialog(ErrorEvent event) {
        showAlertDialog(event.getTitle(), event.getMessage());
    }

    public void showAlertDialog(String title, String message) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);

        alertDialogBuilder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.alert_dialog_ok,
                                   new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog, int id) {
                                           // do nothing
                                       }
                                   }
                ).show();
    }

    public void startTracking() {
        checkWifiEnabled();
        Intent mServiceIntent = new Intent(this, ScanningService.class);
        mServiceIntent.putExtra("strategy", new TrackingStrategy());
        this.startService(mServiceIntent);
        replaceMainFragment(new TrackingFragment());
    }

    public void startLearning() {
        checkWifiEnabled();
        replaceMainFragment(new LearningFragment());
    }

     //TODO create class wifi utils
    private void checkWifiEnabled() {
        checkWifiPermissions();
        final WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainActivity.this);

            alertDialogBuilder.setTitle("Wifi Settings")
                    .setMessage("WiFi must be activated in order to scan for available networks")
                    .setCancelable(false)
                    .setPositiveButton(R.string.activate_wifi,
                                       new DialogInterface.OnClickListener() {
                                           public void onClick(DialogInterface dialog, int id) {
                                               startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                                           }
                                       }
                    ).show();
        }
    }

    //TODO create class wifi utils
    private void checkWifiPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 1
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainActivity.this);

            alertDialogBuilder.setTitle("Permissions not granted")
                    .setMessage("Permissions must be granted in order to scan for available networks")
                    .setCancelable(false)
                    .setPositiveButton(R.string.activate_wifi,
                                       new DialogInterface.OnClickListener() {
                                           public void onClick(DialogInterface dialog, int id) {
                                           }
                                       }
                    ).show();
            Log.e("main activity", "permissions not granted");
        }
    }

    private void replaceMainFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).commit();
    }

    @Override
    public void goToMainFragment() {
        replaceMainFragment(new MainFragment());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    private void selectItem(int position) {

        DrawerItem item = drawerAdapter.getItem(position);
        Fragment fragment = DrawerFragmentsBuilder.getInstance().getFragment(item.getAction());
        replaceMainFragment(fragment);

        mDrawerList.setItemChecked(position, true);
        setTitle(item.getName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        setActionBarTitle(title);
    }

    private void setActionBarTitle(CharSequence title){
        getSupportActionBar().setTitle(title);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
