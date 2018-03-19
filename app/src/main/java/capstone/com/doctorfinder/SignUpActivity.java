package capstone.com.doctorfinder;

import android.media.MediaPlayer;
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

import lib.kingja.switchbutton.SwitchMultiButton;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button SignUpButton;
    private AutoCompleteTextView FirstNameTextView;
    private AutoCompleteTextView EmailTextView;
    private AutoCompleteTextView PasswordTextView;
    private AutoCompleteTextView PhoneNumberTextView;

    private SwitchMultiButton mSwitchMultiButton;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mSwitchMultiButton = findViewById(R.id.radioDoc);
        mSwitchMultiButton.setText("Patient","Doctor");

        SignUpButton =(Button)findViewById(R.id.SignUpButton);
        FirstNameTextView =(AutoCompleteTextView) findViewById(R.id.FirstNameTextView);
        EmailTextView =(AutoCompleteTextView) findViewById(R.id.EmailTextView);
        PasswordTextView =(AutoCompleteTextView) findViewById(R.id.PasswordTextView);
        PhoneNumberTextView =(AutoCompleteTextView) findViewById(R.id.PhoneNumberTextView);

        firebaseAuth = FirebaseAuth.getInstance();



        SignUpButton.setOnClickListener(this);
    }

    private void RegisterUser()
    {
        String Email = EmailTextView.getText().toString().trim();
        String Password = PasswordTextView.getText().toString().trim();
        if(TextUtils.isEmpty(Email))
        {
            Toast.makeText(this,"please enter E-mail",Toast.LENGTH_SHORT);
            return;
        }
        if(TextUtils.isEmpty(Password))
        {
            Toast.makeText(this,"please enter your Password",Toast.LENGTH_SHORT);
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        Toast.makeText(SignUpActivity.this,"completed",Toast.LENGTH_SHORT);
                        if(task.isSuccessful())
                        {
                            //user successfully registered
                            //TO-DO
                            Toast.makeText(SignUpActivity.this,"successful",Toast.LENGTH_SHORT);

                        }
                    }
                });
    }

    @Override
    public void onClick(View v)
    {
        if(v==SignUpButton)
        {
            RegisterUser();
        }

    }
}
