package capstone.com.doctorfinder;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class search extends AppCompatActivity {
    private ArrayList<String> Names = new ArrayList<>();
    private ArrayList<String> Images = new ArrayList<>();
    private ArrayList<String> Addresses = new ArrayList<>();
    private ArrayList<String> DUIDs = new ArrayList<>();
    private ArrayList<Double> ratings = new ArrayList<>();
    private ArrayList<String> result = new ArrayList<>();

    private AutoCompleteTextView SearchTextView;
    private Button SearchButton;
    private RecyclerView recyclerView;
    private DatabaseReference doctorReference;
    private DatabaseReference databaseReference;
    int minimumRating, maximumDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // This part is to change the color of the status bar to match the background ( works only for lollipop or above !!! )
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.myBlue));
        }


        SearchTextView = (AutoCompleteTextView) findViewById(R.id.SearchTextView);
        SearchButton = (Button) findViewById(R.id.SearchButton);
        recyclerView = (RecyclerView) findViewById(R.id.result_list);
        doctorReference = FirebaseDatabase.getInstance().getReference("doctors");
        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoctorSearch(SearchTextView.getText().toString().trim());
            }
        });

        //TODO use these values in the search
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        minimumRating = sharedPreferences.getInt("rating",1);
        maximumDistance = sharedPreferences.getInt("distance",1);

    }

    private void DoctorSearch(final String SearchString) {


        databaseReference.child("doctors").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot doctors) {

                result.clear();
                for (DataSnapshot doctor : doctors.getChildren()) {
                    ArrayList<String> tags = (ArrayList<String>) doctor.child("tags").getValue();
                    for (String tag : tags) {
                        if (tag.contains(SearchString) && result.contains(doctor.getKey()) == false) {
                            result.add(doctor.getKey());
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addDoctor(result);
    }

    void addDoctor(final ArrayList<String> result) {

        databaseReference.child("doctors").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot doctors) {


                Names.clear();
                Images.clear();
                DUIDs.clear();
                Addresses.clear();
                ratings.clear();
                for (DataSnapshot doctor : doctors.getChildren()) {
                    ArrayList<String> tags = (ArrayList<String>) doctor.child("tags").getValue();
                    if (result.contains(doctor.getKey())) {

                        Names.add(doctor.child("full name").getValue(String.class));
                        Images.add(doctor.child("image").getValue(String.class));
                        DUIDs.add(doctor.getKey());
                        Addresses.add(doctor.child("address").getValue(String.class));
                        //TODO fix this
                        //ratings.add(
                        ratings.add(doctor.child("rating").getValue(Double.class));
                        initRecycler();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.result_list);
        SearchRVAdapter adapter = new SearchRVAdapter(this, Names, Images, Addresses, ratings,DUIDs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
