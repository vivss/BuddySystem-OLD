package com.example.vivian.buddysystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.LocationManager;
import android.Manifest;
import android.widget.Toast;


public class Locator extends AppCompatActivity {
    /**** VARIABLE DECLARATIONS ******/
    public static final int REQUEST_LOCATION_PERMISSION = 99;
    public int pingFrequency = 180000;
    public int minDistance = 30;

    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    // listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // TODO: Update location updateNewLocation();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };

    /**** ACTIVITY LIFECYCLE ****/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locator);
        checkLocPermission();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // TODO: Do location stuff like asking for it and doing stuff with it
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**** LOCATION SPECIFIC CHECKING *****/
    // reference: https://stackoverflow.com/questions/40142331/how-to-request-location-permission-at-runtime-on-android-6
    protected boolean checkLocPermission(){
        // Permission not granted
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.location_permission_title)
                        .setMessage(R.string.location_permission_text)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(Locator.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_LOCATION_PERMISSION);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PERMISSION);
            }
            return false;
        }

        // Permission granted
        else {
            return true;
        }
    }
    protected void requestLocation(){
        // GPS enabled and have permission - start requesting location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, pingFrequency, minDistance, locationListener);
    }
}
