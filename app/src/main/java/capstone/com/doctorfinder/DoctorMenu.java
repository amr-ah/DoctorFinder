package capstone.com.doctorfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;



public class DoctorMenu extends AppCompatActivity {


   // android:id="@+id/ScheduleCardView"
    private CardView ScheduleCardView;
    private CardView ProfileCardView;
    private CardView SettingsCardView;
    private CardView SignOutCardView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_menu);

        ScheduleCardView = (CardView) findViewById(R.id.ScheduleCardView);
        ScheduleCardView.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v){
            //TODO schedule activity
        }});

        ProfileCardView = (CardView) findViewById(R.id.ProfileCardView);
        ProfileCardView.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v){
            //TODO profile activity
        }});

        SettingsCardView = (CardView) findViewById(R.id.SettingsCardView);
        SettingsCardView.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v){
            //TODO settings activity
        }});

        SignOutCardView = (CardView) findViewById(R.id.SignOutCardView);
        SignOutCardView.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v){
            SignOut();
        }});
    }

    private void SignOut()
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.getInstance().signOut();
        Intent signout = new Intent(DoctorMenu.this, LoginActivity.class);
        startActivity(signout);
    }

}


