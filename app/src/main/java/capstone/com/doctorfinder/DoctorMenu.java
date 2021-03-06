package capstone.com.doctorfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;



public class DoctorMenu extends AppCompatActivity {


    private CardView ScheduleCardView, AccountCardView;
    private CardView ProfileCardView;
    private CardView SettingsCardView;
    private CardView SignOutCardView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_menu);


        AccountCardView = findViewById(R.id.AccountCardView);
        AccountCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent account = new Intent(DoctorMenu.this,DoctorAccount.class);
                startActivity(account);
            }
        });

        ScheduleCardView = (CardView) findViewById(R.id.ScheduleCardView);
        ScheduleCardView.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v){
            //TODO schedule activity
        }});

        ProfileCardView = (CardView) findViewById(R.id.ProfileCardView);
        ProfileCardView.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v){
            Intent profile = new Intent(DoctorMenu.this,DoctorProfile.class);
            startActivity(profile);
        }});

        SettingsCardView = (CardView) findViewById(R.id.SettingsCardView);
        SettingsCardView.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v){
            Intent settings = new Intent(DoctorMenu.this,SettingsActivity.class);
            startActivity(settings);
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
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged",false);
        editor.commit();
        Intent signout = new Intent(DoctorMenu.this, LoginActivity.class);
        Toast.makeText(DoctorMenu.this, "signed out", Toast.LENGTH_SHORT).show();
        startActivity(signout);
    }

}


