package capstone.com.doctorfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.roughike.swipeselector.SwipeItem;
import com.roughike.swipeselector.SwipeSelector;

public class DoctorSignup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);

        SwipeSelector swipeSelector = findViewById(R.id.swipeSelector);

        String[] categories = {"Swipe to select your category", "Allergist/Immunologist", "Anesthesiologist", "Cardiologist", "Dermatologist", "Family Physician",
                "Gastroenterologist","Generalist", "Hematologist", "Internist", "Nephrologist",
                "Neurologist", "Ophthalmologist", "Pathologist", "Pediatrician", "Psychiatrist",
                "Radiologist", "Rheumatologist", "Sports Medicine Specialist", "Urologist","Other"};

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

      /*  Intent doctor_signup = getIntent();
        String Email =doctor_signup.getStringExtra(SignUpActivity.EXTRA_EMAIL);
        String Password =doctor_signup.getStringExtra(SignUpActivity.EXTRA_PASS);

        Toast.makeText(this, Email +"" +Password, Toast.LENGTH_SHORT).show();*/
    }
}
