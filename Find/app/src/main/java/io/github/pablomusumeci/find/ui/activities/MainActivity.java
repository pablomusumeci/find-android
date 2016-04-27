package io.github.pablomusumeci.find.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
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
import io.github.pablomusumeci.find.ui.events.ScanningCancelled;
import io.github.pablomusumeci.find.ui.fragments.LearningFragment;
import io.github.pablomusumeci.find.ui.fragments.MainFragment;
import io.github.pablomusumeci.find.ui.fragments.TrackingFragment;
import io.github.pablomusumeci.find.ui.model.scanning.LearningStrategy;
import io.github.pablomusumeci.find.ui.model.scanning.TrackingStrategy;
import io.github.pablomusumeci.find.ui.services.ScanningService;
import org.greenrobot.eventbus.EventBus;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceMainFragment(new MainFragment());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getApplication().getApplicationContext());

            alertDialogBuilder.setTitle("Wifi Settings");
            alertDialogBuilder
                    .setMessage("WIFI must be activated")
                    .setCancelable(false);
            alertDialogBuilder.show();

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
