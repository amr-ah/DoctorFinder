package capstone.com.doctorfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void toSignUp(View view) {
        Intent i = new Intent(this,SignUpActivity.class);
        startActivity(i);
    }

    public void toMain(View view) {
    }
}
