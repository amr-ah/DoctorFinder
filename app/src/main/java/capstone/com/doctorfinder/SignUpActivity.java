package capstone.com.doctorfinder;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import lib.kingja.switchbutton.SwitchMultiButton;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_EMAIL = "SIGNUP_EXTRA_EMAIL";
    public static final String EXTRA_PASS = "SIGNUP_EXTRA_PASS";
    public static final String EXTRA_NAME = "SIGNUP_EXTRA_NAME";
    public static final String EXTRA_NUM = "SIGNUP_EXTRA_NUM";

    private Button SignUpButton;
    private AutoCompleteTextView FullNameTextView;
    private AutoCompleteTextView EmailTextView;
    private AutoCompleteTextView PasswordTextView;
    private AutoCompleteTextView PhoneNumberTextView;

    private SwitchMultiButton mSwitchMultiButton;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        mSwitchMultiButton = findViewById(R.id.radioDoc);
        mSwitchMultiButton.setText("Patient", "Doctor");


        FullNameTextView = (AutoCompleteTextView) findViewById(R.id.FullNameTextView);
        EmailTextView = (AutoCompleteTextView) findViewById(R.id.EmailTextView);
        PasswordTextView = (AutoCompleteTextView) findViewById(R.id.PasswordTextView);
        PhoneNumberTextView = (AutoCompleteTextView) findViewById(R.id.PhoneNumberTextView);

        SignUpButton = (Button) findViewById(R.id.SignUpButton);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Register();
            }
        });
    }

    private void Register() {
        String Email = EmailTextView.getText().toString().trim();
        String Password = PasswordTextView.getText().toString().trim();

        //not used yet
        String FullName = FullNameTextView.getText().toString().trim();
        String PhoneNumber = PhoneNumberTextView.getText().toString().trim();

        if (TextUtils.isEmpty(FullName)) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Please enter E-mail", Toast.LENGTH_SHORT).show();
            return;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            Toast.makeText(this, "E-mail format is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Please enter your Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(PhoneNumber)) {
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mSwitchMultiButton.getSelectedTab() == 0) {
            firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(SignUpActivity.this, "Sign up completed", Toast.LENGTH_SHORT).show();
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("patients").child(firebaseAuth.getCurrentUser().getUid()).child("full name").setValue(FullNameTextView.getText().toString().trim());
                                mDatabase.child("patients").child(firebaseAuth.getCurrentUser().getUid()).child("phone number").setValue(PhoneNumberTextView.getText().toString().trim());
                                Intent login = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(login);
                            } else {

                                Toast.makeText(SignUpActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Intent doctor_signup = new Intent(SignUpActivity.this, DoctorSignup.class);

            doctor_signup.putExtra("SIGNUP_EXTRA_EMAIL", Email);
            doctor_signup.putExtra("SIGNUP_EXTRA_PASS", Password);
            doctor_signup.putExtra("SIGNUP_EXTRA_NAME", FullNameTextView.getText().toString().trim());
            doctor_signup.putExtra("SIGNUP_EXTRA_NUM", PhoneNumberTextView.getText().toString().trim());

            startActivity(doctor_signup);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == SignUpButton) {
        }

    }
}
