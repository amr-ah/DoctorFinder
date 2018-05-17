package capstone.com.doctorfinder;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ankit.gpslibrary.MyTracker;

import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;

public class DoctorAccount extends AppCompatActivity {

    int LOCATION_ACCESS;
    double longitude, latitude;

    TextView name;
    AutoCompleteTextView oldAddress;
    AutoCompleteTextView oldPersonalPhone;
    AutoCompleteTextView oldWorkPhone;
    MultiAutoCompleteTextView oldbio;
    private Button SaveButton;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    String D_ID;
    String addressString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = firebaseAuth.getInstance();
        name = findViewById(R.id.oldName);
        oldPersonalPhone = findViewById(R.id.oldPersonalPhone);
        oldWorkPhone = findViewById(R.id.oldWorkPhone);
        oldAddress = findViewById(R.id.oldAddress);
        oldbio = findViewById(R.id.oldBio);

        D_ID = firebaseAuth.getCurrentUser().getUid().toString().trim();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                name.setText(snapshot.child("doctors").child(D_ID).child("full name").getValue(String.class));
                oldbio.setText(snapshot.child("doctors").child(D_ID).child("bio").getValue(String.class));
                oldPersonalPhone.setText(snapshot.child("doctors").child(D_ID).child("phone number").getValue(String.class));
                oldWorkPhone.setText(snapshot.child("doctors").child(D_ID).child("work number").getValue(String.class));
                addressString = snapshot.child("doctors").child(D_ID).child("address").getValue(String.class);
                oldAddress.setText(addressString);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        SaveButton = (Button) findViewById(R.id.SaveButton);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });



    }

    private void saveChanges()
    {
        //TODO UPDATE DATABASE
        mDatabase.child("doctors").child(D_ID).child("address").setValue(oldAddress.getText().toString().trim());
        mDatabase.child("doctors").child(D_ID).child("phone number").setValue(oldPersonalPhone.getText().toString().trim());
        mDatabase.child("doctors").child(D_ID).child("work number").setValue(oldWorkPhone.getText().toString().trim());
        mDatabase.child("doctors").child(D_ID).child("bio").setValue(oldbio.getText().toString().trim());
        Toast.makeText(DoctorAccount.this, "changes saved", Toast.LENGTH_SHORT).show();
    }

    public void getCurrentLocation(View view) {

        String[] perms = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if (EasyPermissions.hasPermissions(this, perms)) {
            MyTracker position = new MyTracker(this);

            longitude = position.getLongitude();
            latitude = position.getLatitude();
            Log.e("Permission", longitude + " " + latitude);
        } else {
            EasyPermissions.requestPermissions(this, "In order to access your location, you need to approve the permission !", LOCATION_ACCESS, perms);
        }

        //TODO Save longitude and latitude in the database.

    }
}
