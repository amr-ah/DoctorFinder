package capstone.com.doctorfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class search extends AppCompatActivity {

    private AutoCompleteTextView SearchTextView;
    private Button SearchButton;
    private RecyclerView ResultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchTextView = (AutoCompleteTextView)findViewById(R.id.SearchTextView);
        SearchButton = (Button) findViewById(R.id.SearchButton);
        ResultList = (RecyclerView) findViewById(R.id.result_list);

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireBaseDoctorSearch();
            }
        });
    }

    private void fireBaseDoctorSearch() {



    }

}
