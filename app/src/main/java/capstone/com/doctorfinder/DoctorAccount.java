package capstone.com.doctorfinder;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.ankit.gpslibrary.MyTracker;

import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;

public class DoctorAccount extends AppCompatActivity {

    int LOCATION_ACCESS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account);


    }

    public void getCurrentLocation(View view) {

        String[] perms = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
        double longitude, latitude;

        if (EasyPermissions.hasPermissions(this,perms))
        {
            MyTracker position = new MyTracker(this);

            longitude = position.getLongitude();
            latitude = position.getLatitude();
        }
        else
        {
            EasyPermissions.requestPermissions(this,"In order to access your location, you need to approve the permission !",LOCATION_ACCESS,perms);
        }

        //TODO Save longitude and latitude in the database.

    }
}
