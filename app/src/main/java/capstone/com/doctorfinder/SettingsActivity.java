package capstone.com.doctorfinder;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class SettingsActivity extends AppCompatActivity {

    boolean isDoctor = false;
    LinearLayout mLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mLinearLayout = findViewById(R.id.settingBackground);

        //TODO check the user type (doctor or patient) via sharedPreferences in order to load the appropriate colors
        checkUserType(isDoctor);


    }

    void checkUserType(boolean type){

        if(type)
            return;
        else {
            mLinearLayout.setBackgroundColor(Color.parseColor("#152a39"));
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(this,R.color.myBlue));
            }
        }

    }
}
