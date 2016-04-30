package io.github.pablomusumeci.find.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import io.github.pablomusumeci.find.R;
import io.github.pablomusumeci.find.domain.events.ErrorEvent;
import io.github.pablomusumeci.find.domain.events.ScanningCancelled;
import io.github.pablomusumeci.find.domain.model.scanning.LearningStrategy;
import io.github.pablomusumeci.find.domain.model.scanning.TrackingStrategy;
import io.github.pablomusumeci.find.domain.services.background.ScanningService;
import io.github.pablomusumeci.find.ui.fragments.LearningFragment;
import io.github.pablomusumeci.find.ui.fragments.MainFragment;
import io.github.pablomusumeci.find.ui.fragments.TrackingFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        replaceMainFragment(new MainFragment());
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

    private void showAlertDialog(String title, String message) {

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

    public void track(View view) {
        checkWifiEnabled();
        Intent mServiceIntent = new Intent(this, ScanningService.class);
        mServiceIntent.putExtra("strategy", new TrackingStrategy());
        this.startService(mServiceIntent);
        replaceMainFragment(new TrackingFragment());
    }

    public void learn(View view) {
        checkWifiEnabled();
        replaceMainFragment(new LearningFragment());
    }

    public void returnFromTracking(View view) {

        EventBus.getDefault().post(new ScanningCancelled());
        replaceMainFragment(new MainFragment());
    }

    public void returnFromLearning(View view) {

        EventBus.getDefault().post(new ScanningCancelled());
        replaceMainFragment(new MainFragment());
    }

    public void startLearning(View view) {
        EditText learningLocation = (EditText) findViewById(R.id.learning_location_value);
        if (learningLocation.getText().toString().isEmpty()) {
            Toast.makeText(this, "You must enter a location", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent mServiceIntent = new Intent(this, ScanningService.class);
            mServiceIntent.putExtra("strategy", new LearningStrategy());
            this.startService(mServiceIntent);
        }
    }

    private void checkWifiEnabled() {
        checkWifiPermissions();
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            showAlertDialog("Wifi Settings", "WIFI must be activated");
            startActivity(new Intent(wifiManager.ACTION_PICK_WIFI_NETWORK));
        }
    }

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
            Log.e("main activity", "perm not granted");

        }
    }

    private void replaceMainFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).commit();
    }
}
