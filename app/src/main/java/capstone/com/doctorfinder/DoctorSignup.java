package capstone.com.doctorfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DoctorSignup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);

        Intent doctor_signup = getIntent();
        String Email =doctor_signup.getStringExtra(SignUpActivity.EXTRA_EMAIL);
        String Password =doctor_signup.getStringExtra(SignUpActivity.EXTRA_PASS);

        Toast.makeText(this, Email +"" +Password, Toast.LENGTH_SHORT).show();
    }
}
