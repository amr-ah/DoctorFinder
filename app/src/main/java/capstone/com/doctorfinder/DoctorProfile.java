package capstone.com.doctorfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

public class DoctorProfile extends AppCompatActivity {

    SmileRating mSmileRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        //TODO check the documentation for the expander in here https://android-arsenal.com/details/1/6662

        mSmileRating = findViewById(R.id.smileyRating);
        mSmileRating.setSelectedSmile(BaseRating.OKAY);
    }
}
