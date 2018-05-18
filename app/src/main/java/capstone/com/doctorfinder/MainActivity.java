package capstone.com.doctorfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    boolean isDoctor;
    boolean logged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
                isDoctor = sharedPreferences.getBoolean("isDoctor",false);
                logged = sharedPreferences.getBoolean("logged",false);
                if(logged){
                    if (isDoctor){
                        Intent doctor = new Intent(MainActivity.this,DoctorMenu.class);
                        startActivity(doctor);
                        finish();
                    }
                    else {
                        Intent patient = new Intent(MainActivity.this, PatientMenu.class);
                        startActivity(patient);
                        finish();

                    }

                }
                else {

                Intent login = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(login);
                finish();
                }
            }
        },SPLASH_TIME_OUT);
    }
}
