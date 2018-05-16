package capstone.com.doctorfinder;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class PatientMenu extends AppCompatActivity {

    private CardView SearchCardView;
    private CardView FilterCardView;
    private CardView PSettingsCardView;
    private CardView PSignOutCardView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_menu);

        //This part is to change the color of the status bar to match the background ( works only for lollipop or above !!! )
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.myBlue));
        }

        SearchCardView = (CardView) findViewById(R.id.SearchCardView);
        SearchCardView.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v){
            Intent search = new Intent(PatientMenu.this,search.class);
            startActivity(search);
        }});

        FilterCardView = (CardView) findViewById(R.id.FilterCardView);
        FilterCardView.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v){
            Intent filter = new Intent(PatientMenu.this,FilterActivity.class);
            startActivity(filter);
        }});

        PSettingsCardView = (CardView) findViewById(R.id.PSettingsCardView);
        PSettingsCardView.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v){
            Intent settings = new Intent(PatientMenu.this, SettingsActivity.class);
            startActivity(settings);
        }});

        PSignOutCardView = (CardView) findViewById(R.id.PSignOutCardView);
        PSignOutCardView.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v){
            SignOut();
        }});
    }

    private void SignOut()
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.getInstance().signOut();
        Intent signout = new Intent(PatientMenu.this, LoginActivity.class);
        Toast.makeText(PatientMenu.this, "signed out", Toast.LENGTH_SHORT).show();
        startActivity(signout);
    }
}
