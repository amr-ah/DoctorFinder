package capstone.com.doctorfinder;

import android.*;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import app.youkai.progressview.ProgressView;
import pub.devrel.easypermissions.EasyPermissions;

public class FilterActivity extends AppCompatActivity {

    private SmileRating mSmileRating;
    private ProgressView mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mSmileRating = findViewById(R.id.filterRating);
        mSmileRating.setSelectedSmile(BaseRating.OKAY);

        mProgressView = findViewById(R.id.km);
        mProgressView.setProgress(0);

        //To get the value of the distance use mProgress.getProgress();

        //This part is to change the color of the status bar to match the background ( works only for lollipop or above !!! )
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.myBlue));
        }





    }
}
