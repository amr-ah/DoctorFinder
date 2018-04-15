package capstone.com.doctorfinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private AutoCompleteTextView EmailTextView;
    private AutoCompleteTextView PasswordTextView;
    private Button LoginButton;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EmailTextView =(AutoCompleteTextView) findViewById(R.id.EmailTextView);
        PasswordTextView =(AutoCompleteTextView) findViewById(R.id.PasswordTextView);

        firebaseAuth = firebaseAuth.getInstance();


        LoginButton = (Button) findViewById(R.id.LoginButton);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Patientlogin();
            }
        });


    }

    public void toSignUp(View view) {
        Intent i = new Intent(this,SignUpActivity.class);
        startActivity(i);
    }

    public void Patientlogin()
    {
        String Email = EmailTextView.getText().toString().trim();
        String Password = PasswordTextView.getText().toString().trim();

        if(TextUtils.isEmpty(Email))
        {
            Toast.makeText(this,"please enter E-mail",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Password))
        {
            Toast.makeText(this,"please enter your Password",Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(LoginActivity.this, "Signing in", Toast.LENGTH_SHORT).show();

        firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful()) {
                    //user successfully registered

                    Toast.makeText(LoginActivity.this, "done", Toast.LENGTH_SHORT).show();
                    //TODO  user should be redirected to the search page(initial page for the user after log in)\
                    Intent login = new Intent(LoginActivity.this,user_main.class);
                    startActivity(login);

                } else {
                    Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    public void toMain(View view) {
    }

    @Override
    public void onClick(View v)
    {

        if(v==LoginButton) {

            Patientlogin();
        }
    }
}
