package capstone.com.doctorfinder;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roughike.swipeselector.SwipeItem;
import com.roughike.swipeselector.SwipeSelector;

public class DoctorSignup extends AppCompatActivity {

    private MultiAutoCompleteTextView AddressTextView;
    private AutoCompleteTextView WorkNumTextView;
    private AutoCompleteTextView TagsTextView;
    private Button AddTagButton;
    private MultiAutoCompleteTextView BioTextView;
    private Button FinishButton;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        Intent doctor_signup = getIntent();
        final String Email = doctor_signup.getStringExtra(SignUpActivity.EXTRA_EMAIL);
        final String Password = doctor_signup.getStringExtra(SignUpActivity.EXTRA_PASS);
        final String Name = doctor_signup.getStringExtra(SignUpActivity.EXTRA_NAME);
        final String Num = doctor_signup.getStringExtra(SignUpActivity.EXTRA_NUM);

                SwipeSelector swipeSelector = findViewById(R.id.swipeSelector);
        String[] categories = {"Swipe to select your category", "Allergist/Immunologist", "Anesthesiologist", "Cardiologist", "Dermatologist", "Family Physician",
                "Gastroenterologist", "Generalist", "Hematologist", "Internist", "Nephrologist",
                "Neurologist", "Ophthalmologist", "Pathologist", "Pediatrician", "Psychiatrist",
                "Radiologist", "Rheumatologist", "Sports Medicine Specialist", "Urologist", "Other"};
        swipeSelector.setItems(
                new SwipeItem(0, categories[0], ""),
                new SwipeItem(1, categories[1], ""),
                new SwipeItem(2, categories[2], ""),
                new SwipeItem(3, categories[3], ""),
                new SwipeItem(4, categories[4], ""),
                new SwipeItem(5, categories[5], ""),
                new SwipeItem(6, categories[6], ""),
                new SwipeItem(7, categories[7], ""),
                new SwipeItem(8, categories[8], ""),
                new SwipeItem(9, categories[9], ""),
                new SwipeItem(10, categories[10], ""),
                new SwipeItem(11, categories[11], ""),
                new SwipeItem(12, categories[12], ""),
                new SwipeItem(13, categories[13], ""),
                new SwipeItem(14, categories[14], ""),
                new SwipeItem(15, categories[15], ""),
                new SwipeItem(16, categories[16], ""),
                new SwipeItem(17, categories[17], ""),
                new SwipeItem(18, categories[18], ""),
                new SwipeItem(19, categories[19], ""),
                new SwipeItem(20, categories[20], "")
        );


        AddressTextView = (MultiAutoCompleteTextView) findViewById(R.id.AddressTextView);
        WorkNumTextView = (AutoCompleteTextView) findViewById(R.id.WorkNumTextView);
        TagsTextView = (AutoCompleteTextView) findViewById(R.id.TagsTextView);
        AddTagButton = (Button) findViewById(R.id.AddTagButton);
        BioTextView = (MultiAutoCompleteTextView) findViewById(R.id.BioTextView);
        FinishButton = (Button) findViewById(R.id.FinishButton);

        FinishButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                RegisterDoctor(Email, Password,Name,Num);

            }
        });

    }

    private void RegisterDoctor(String Email, String Password, final String FullName, final String Number) {

        //TODO add validation for category
        //String category = .getText().toString().trim();
        String Address = AddressTextView.getText().toString().trim();
        String WorkNum = WorkNumTextView.getText().toString().trim();
        //TODO count tags for validation (minimum is 3)
        //String Tags = TagsTextView.getText().toString().trim();
        String Bio = BioTextView.getText().toString().trim();

        if (TextUtils.isEmpty(Address)) {
            Toast.makeText(this, "Please enter your adress", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(WorkNum)) {
            Toast.makeText(this, "Please enter work phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Bio)) {
            Toast.makeText(this, "Please enter your bio", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            Toast.makeText(DoctorSignup.this, "Sign up completed", Toast.LENGTH_SHORT).show();
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("doctors").child(firebaseAuth.getCurrentUser().getUid()).child("full name").setValue(FullName);
                            mDatabase.child("doctors").child(firebaseAuth.getCurrentUser().getUid()).child("phone number").setValue(Number);
                            mDatabase.child("doctors").child(firebaseAuth.getCurrentUser().getUid()).child("address").setValue(AddressTextView.getText().toString().trim());
                            mDatabase.child("doctors").child(firebaseAuth.getCurrentUser().getUid()).child("work number").setValue(WorkNumTextView.getText().toString().trim());
                            mDatabase.child("doctors").child(firebaseAuth.getCurrentUser().getUid()).child("bio").setValue(BioTextView.getText().toString().trim());

                            //Intent login = new Intent(DoctorSignup.this, //TODO here we put the doctors main activity);
                           // startActivity(login);
                        } else {

                            //Toast.makeText(SignUpActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
