package capstone.com.doctorfinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RetrievePassword extends AppCompatActivity {

    private AutoCompleteTextView email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);

        email = findViewById(R.id.txtUserEmail);
        mAuth = FirebaseAuth.getInstance();
    }

    public void sendConfirmation(View view) {

        // This method sends verification email to reset password when button is clicked

        String uEmail = email.getText().toString().trim();

        if(TextUtils.isEmpty(uEmail)){
            Toast.makeText(this,"Please enter your email !", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(uEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RetrievePassword.this,"Check your email to reset your password!",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(RetrievePassword.this, "Failed to send reset password email!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
