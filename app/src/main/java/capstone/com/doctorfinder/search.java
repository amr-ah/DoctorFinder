package capstone.com.doctorfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class search extends AppCompatActivity {

   private AutoCompleteTextView SearchTextView;
    private Button SearchButton;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    //private FirebaseUser firebaseuser;

    private ArrayList<String> doctorsName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchTextView = (AutoCompleteTextView)findViewById(R.id.SearchTextView);
        SearchButton = (Button) findViewById(R.id.SearchButton);
        recyclerView = (RecyclerView) findViewById(R.id.result_list);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //firebaseuser = FirebaseAuth.getInstance().getCurrentUser();

        doctorsName = new ArrayList<>();



        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoctorSearch(SearchTextView.getText().toString().trim());
            }
        });
    }

    private void DoctorSearch(String S) {
        //TODO (annas) design the items for the recyclelistview so i can use it here
        //TODO retrieve the doctors info according to the searched terms (look into the tags and the category of each doctor)

       databaseReference.child("doctors").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                doctorsName.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    doctorsName.add(snapshot.child("full name").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
