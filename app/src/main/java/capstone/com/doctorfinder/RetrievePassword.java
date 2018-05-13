package capstone.com.doctorfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RetrievePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
    }

    public void sendConfirmation(View view) {
        //TODO here write methods to send email to user to refresh his password

        Toast.makeText(this,"Check your email for confirmation",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(RetrievePassword.this,LoginActivity.class);
        startActivity(i);
        finish();
    }
}
