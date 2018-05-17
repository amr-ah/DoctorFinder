package capstone.com.doctorfinder;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorProfile extends AppCompatActivity {

    SmileRating mSmileRating;
    private TextView name;
    private TextView address;
    private TextView phoneNum;
    private TextView workNum;
    private TextView rating;
    private CircleImageView image;
    private Button MapButton;
    private Button submitButton;
    private TextView bio;
    private String addressString;
    private String D_ID;
    private MultiAutoCompleteTextView comment;
    int rate;
    int count;
    final String P_ID = "JImrwzglLjMB9AkdNgp7SQYw78X2";
    private Context mContext;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("search",MODE_PRIVATE);
        D_ID = sharedPreferences.getString("userId","");

        //TODO get Doctor ID instead of fixed value
        //D_ID = "31hy02F9mxV0DiL963uthIFnQ1h2";

        //TODO check the documentation for the expander in here https://android-arsenal.com/details/1/6662
        mSmileRating = findViewById(R.id.smileyRating);
        mSmileRating.setSelectedSmile(BaseRating.OKAY);

        //initioalize
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mContext = this;

        rating = (TextView) findViewById(R.id.DRatingTextView);
        image = findViewById(R.id.DCircleImage);
        //TODO verified
        name = (TextView) findViewById(R.id.DNameTextView);
        bio = (TextView) findViewById(R.id.DBioTextView);
        phoneNum = (TextView) findViewById(R.id.DPhoneNumber);
        workNum = (TextView) findViewById(R.id.DWorkNumber);
        address = (TextView) findViewById(R.id.DAdressTextView);

        comment = (MultiAutoCompleteTextView) findViewById(R.id.CommentTextView);
        mSmileRating = (SmileRating)  findViewById(R.id.smileyRating);




        mDatabase.child("doctors").child(D_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot doctor) {

                name.setText(doctor.child("full name").getValue(String.class));
                bio.setText(doctor.child("bio").getValue(String.class));
                phoneNum.setText(doctor.child("phone number").getValue(String.class));
                workNum.setText(doctor.child("work number").getValue(String.class));
                addressString = doctor.child("address").getValue(String.class);
                address.setText(addressString);
                rating.setText(doctor.child("rating").getValue(Double.class).toString());

                Glide.with(mContext).load(doctor.child("image").getValue(String.class)).into(image);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        MapButton = (Button) findViewById(R.id.mapButton);
        MapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
        submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComment();
            }
        });
    }

    private void submitComment()
    {

        //TODO get Patient ID instead of fixed value

        if(TextUtils.isEmpty(comment.getText()))
        {
            updateRating();
            Toast.makeText(DoctorProfile.this, "only review submitted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot doctor) {
                    mDatabase.child("doctors").child(D_ID).child("comment").setValue(comment.getText().toString());
                    mDatabase.child("doctors").child(D_ID).child("reviews").child(P_ID).setValue(mSmileRating.getRating());
                    Toast.makeText(DoctorProfile.this, "comment and review submitted", Toast.LENGTH_SHORT).show();
                    mSmileRating.setSelectedSmile(BaseRating.OKAY);
                    comment.setText("");
                    updateRating();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void updateRating()
    {
        rate =0;
        count=0;
        mDatabase.child("doctors").child(D_ID).child("reviews").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot reviews) {


                mDatabase.child("doctors").child(D_ID).child("reviews").child(P_ID).setValue(mSmileRating.getRating());
                for (DataSnapshot review : reviews.getChildren()) {

                    rate += review.getValue(Integer.class);
                    count ++;
                }
               /* if(count==0)
                {
                    count=1;
                }*/
                double r = rate/count;
               //mDatabase.child("doctors").child(D_ID).child("rating").setValue(rating);
                mDatabase.child("doctors").child(D_ID).child("rating").setValue(r);
                rating.setText(Double.toString(r));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    private void openMap() {
        //TODO get the co-ordinations and redirect to the maps app
        //this code opens map intent with a given text location

        addressString = "geo:0,0?q=" + addressString;
        Uri IntentUri = Uri.parse(addressString);
        //AddressTextView.setText("Coo is = "+IntentUri);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        mapIntent.setData(IntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
