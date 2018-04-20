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
                if (mSwitchMultiButton.getSelectedTab() == 0) {
                    RegisterPatient();
                } else {
                    //TODO figure how to send email and password information to the other activity
                    Intent doctor_signup = new Intent(SignUpActivity.this, DoctorSignup.class);
                    startActivity(doctor_signup);

                }

            }
        });
    }

    private void RegisterPatient() {
        String Email = EmailTextView.getText().toString().trim();
        String Password = PasswordTextView.getText().toString().trim();

        //not used yet
        String FullName = FullNameTextView.getText().toString().trim();
        String PhoneNumber = PhoneNumberTextView.getText().toString().trim();

        //TODO make sure that user enters a valid email and phone number

        if (TextUtils.isEmpty(FullName)) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Please enter E-mail", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void onClick(View v) {
        if (v == SignUpButton) {
        }

    }
}
