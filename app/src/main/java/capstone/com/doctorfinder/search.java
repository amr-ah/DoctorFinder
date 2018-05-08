package capstone.com.doctorfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.ArrayList;

public class search extends AppCompatActivity
{

    private ArrayList<String> Names = new ArrayList<>();
    private ArrayList<String> Images = new ArrayList<>();
    private ArrayList<String> Addresses = new ArrayList<>();
    private ArrayList<String> DUIDs = new ArrayList<>();
    private ArrayList<Double> ratings = new ArrayList<>();

    private AutoCompleteTextView SearchTextView;
    private Button SearchButton;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    //private FirebaseUser firebaseuser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchTextView = (AutoCompleteTextView)findViewById(R.id.SearchTextView);
        SearchButton = (Button) findViewById(R.id.SearchButton);
        recyclerView = (RecyclerView) findViewById(R.id.result_list);
        databaseReference = FirebaseDatabase.getInstance().getReference("doctors");
        //firebaseuser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoctorSearch(SearchTextView.getText().toString().trim());
            }
        });
    }

    private void DoctorSearch(String S)
    {

        //TODO (annas) design the items for the recyclelistview so i can use it here
        //TODO retrieve the doctors info according to the searched terms (look into the tags and the category of each doctor)

       databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Names.clear();
                Images.clear();
                DUIDs.clear();
                Addresses.clear();
                ratings.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Names.add(snapshot.child("full name").getValue(String.class));
                    //TODO get image link after its added to the database
                    //Images.add(snapshot.child("").getValue(String.class));
                    //DUIDs.add("image");
                    Addresses.add(snapshot.child("address").getValue(String.class));
                    //TODO look for how you can cast to a double
                    //ratings.add(snapshot.child("rating").getValue(String.class));
                    initRecycler();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.result_list);
        //TODO send arrays here
        SearchRVAdapter adapter = new SearchRVAdapter(this,Names,Images,Addresses,ratings);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
