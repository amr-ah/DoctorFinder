package capstone.com.doctorfinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;
import java.util.Random;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roughike.swipeselector.SwipeItem;
import com.roughike.swipeselector.SwipeSelector;

import java.util.ArrayList;

public class DoctorSignup extends AppCompatActivity {

    private SwipeSelector swipeSelector;
    private MultiAutoCompleteTextView AddressTextView;
    private AutoCompleteTextView WorkNumTextView;
    private AutoCompleteTextView TagsTextView;
    private MultiAutoCompleteTextView BioTextView;
    private AutoCompleteTextView mAutoCompleteTextView;
    private int val;
    private int tagscount;
    private String Email, Password, Name, Num, category, option;
    private SwipeItem selected;
    private ArrayList<String> tags = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private String[] defaultimages = {"https://firebasestorage.googleapis.com/v0/b/splendid-window-195220.appspot.com/o/1.jpg?alt=media&token=16edde89-f31f-4dca-a887-50ddaa4e2e50"
            ,"https://firebasestorage.googleapis.com/v0/b/splendid-window-195220.appspot.com/o/2.jpg?alt=media&token=1a337d95-4e73-46cd-9b45-b3a70c451581",
            "https://firebasestorage.googleapis.com/v0/b/splendid-window-195220.appspot.com/o/3.jpg?alt=media&token=a3057d23-db83-443d-97e6-6cc93a96d78f"};

    private String[] categories = {"Swipe to select your category", "Allergist/Immunologist", "Anesthesiologist", "Cardiologist", "Dermatologist", "Family Physician",
            "Gastroenterologist", "Generalist", "Hematologist", "Internist", "Nephrologist",
            "Neurologist", "Ophthalmologist", "Pathologist", "Pediatrician", "Psychiatrist",
            "Radiologist", "Rheumatologist", "Sports Medicine Specialist", "Urologist", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        swipeSelector = findViewById(R.id.swipeSelector);

        Intent doctor_signup = getIntent();
        Email = doctor_signup.getStringExtra(SignUpActivity.EXTRA_EMAIL);
        Password = doctor_signup.getStringExtra(SignUpActivity.EXTRA_PASS);
        Name = doctor_signup.getStringExtra(SignUpActivity.EXTRA_NAME);
        Num = doctor_signup.getStringExtra(SignUpActivity.EXTRA_NUM);
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

        tagscount = 0;
        AddressTextView = findViewById(R.id.AddressTextView);
        WorkNumTextView = findViewById(R.id.WorkNumTextView);
        TagsTextView = findViewById(R.id.TagsTextView);
        BioTextView = findViewById(R.id.BioTextView);
        mAutoCompleteTextView = findViewById(R.id.newCategory);
        initRecycler();

    }

    private void RegisterDoctor(String Email, String Password, final String FullName, final String Number, final String Category) {


        String Address = AddressTextView.getText().toString().trim();
        String WorkNum = WorkNumTextView.getText().toString().trim();
        String Bio = BioTextView.getText().toString().trim();
        category = mAutoCompleteTextView.getText().toString().trim();
        selected = swipeSelector.getSelectedItem();
        val = (int) selected.value;

        if (TextUtils.isEmpty(Address)) {
            Toast.makeText(this, "Please enter your adress", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tagscount < 3) {
            Toast.makeText(this, "Please enter at least 3 tags", Toast.LENGTH_SHORT).show();
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
        if (val == 0) {
            Toast.makeText(this, "Please select your category", Toast.LENGTH_SHORT).show();
            return;
        }
        if (val == 20) {                                                                        //this one checks if the user chose 'Other'
            mAutoCompleteTextView.setVisibility(View.VISIBLE);                              //makes the category textview visible

            if (TextUtils.isEmpty(category)) {
                Toast.makeText(DoctorSignup.this, "Enter your Category", Toast.LENGTH_SHORT).show();
                return;
            } else {
                option = category;
            }

        }
        if (val != 20) {                                        //checks again if the user changes his swipe selection
            mAutoCompleteTextView.setVisibility(View.GONE);
            option = selected.title;

        }
        if (val == 20) {
            category = mAutoCompleteTextView.getText().toString().trim();

        } else {
            category = categories[(int) swipeSelector.getSelectedItem().value];
        }

        firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(DoctorSignup.this, "Sign up completed", Toast.LENGTH_SHORT).show();
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            int c = 0;

                            while (tags.size() > c) {
                                String element = firebaseAuth.getCurrentUser().getUid().toString();
                                mDatabase.child("tags").child(tags.get(c)).child(element).setValue(element);
                                c++;
                            }
                            mDatabase.child("doctors").child(firebaseAuth.getCurrentUser().getUid()).child("full name").setValue(FullName);
                            mDatabase.child("doctors").child(firebaseAuth.getCurrentUser().getUid()).child("category").setValue(category);
                            mDatabase.child("doctors").child(firebaseAuth.getCurrentUser().getUid()).child("address").setValue(AddressTextView.getText().toString().trim());
                            mDatabase.child("doctors").child(firebaseAuth.getCurrentUser().getUid()).child("phone number").setValue(Number);
                            mDatabase.child("doctors").child(firebaseAuth.getCurrentUser().getUid()).child("work number").setValue(WorkNumTextView.getText().toString().trim());
                            mDatabase.child("doctors").child(firebaseAuth.getCurrentUser().getUid()).child("bio").setValue(BioTextView.getText().toString().trim());
                            mDatabase.child("doctors").child(firebaseAuth.getCurrentUser().getUid()).child("tags").setValue(tags);
                            mDatabase.child("doctors").child(firebaseAuth.getCurrentUser().getUid()).child("rating").setValue(0.0);
                            //sets default image randomly
                            Random rand = new Random();
                            int n=rand.nextInt(3);
                            mDatabase.child("doctors").child(firebaseAuth.getCurrentUser().getUid()).child("image").
                                    setValue(defaultimages[n]);

                            Intent DoctorLogin = new Intent(DoctorSignup.this,DoctorMenu.class );
                            startActivity(DoctorLogin);
                        } else {

                            Toast.makeText(DoctorSignup.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, tags);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void checkDoctor(View view) {
        RegisterDoctor(Email, Password, Name, Num, option);
    }

    public void addTag(View view) {
        String newTag = TagsTextView.getText().toString().trim();
        if (TextUtils.isEmpty(newTag)) {
            Toast.makeText(DoctorSignup.this, "Please enter a tag so patients can find you !", Toast.LENGTH_SHORT).show();
            return;
        }
        tags.add(newTag);
        tagscount++;
        initRecycler();
        TagsTextView.setText("");

    }

}
