package capstone.com.doctorfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by amr on 5/8/18.
 */

public class SearchRVAdapter extends RecyclerView.Adapter<SearchRVAdapter.ViewHolder> {

    private ArrayList<String> Names = new ArrayList<>();
    private ArrayList<String> Images = new ArrayList<>();
    private ArrayList<String> Addresses = new ArrayList<>();
    private ArrayList<Double> ratings = new ArrayList<>();
    private ArrayList<String> userId = new ArrayList<>();
    private Context mContext;




    public SearchRVAdapter(Context mContext,ArrayList<String> names, ArrayList<String> images, ArrayList<String> addresses,ArrayList<Double> ratings, ArrayList<String> Id) {
        Names = names;
        Images = images;
        Addresses = addresses;
        userId = Id;
        this.ratings = ratings;
        this.mContext = mContext;
    }
    @Override
    public SearchRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        SearchRVAdapter.ViewHolder holder = new SearchRVAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SearchRVAdapter.ViewHolder holder, final int position) {

        holder.name.setText(Names.get(position));
        holder.address.setText(Addresses.get(position));
        holder.rating.setText(ratings.get(position).toString());
        Glide.with(mContext).load(Images.get(position)).into(holder.image);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUid(holder.getAdapterPosition());
                Intent i = new Intent(mContext,DoctorProfile.class);
                mContext.startActivity(i);
            }
        });


    }


    public void sendUid(int position){

        String id = userId.get(position).trim().toString();
        Log.d("app",id);
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences("search",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("userId",id);
        editor.commit();

    }

    @Override
    public int getItemCount() {
        return Names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView address;
        TextView rating;
        CircleImageView image;
        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);

            mContext = itemView.getContext();
             name = itemView.findViewById(R.id.NameTextView);
            address = itemView.findViewById(R.id.AdressTextView);
            rating = itemView.findViewById(R.id.RatingTextView);
             image = itemView.findViewById(R.id.CircleImage);
             mCardView = itemView.findViewById(R.id.search_item);
        }
    }
}
