package capstone.com.doctorfinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnCloseClickListener;

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

    private Context mContext;

    public SearchRVAdapter(Context mContext,ArrayList<String> names, ArrayList<String> images, ArrayList<String> addresses,ArrayList<Double> ratings) {
        Names = names;
        Images = images;
        Addresses = addresses;
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
    public void onBindViewHolder(SearchRVAdapter.ViewHolder holder, int position) {

        holder.name.setText(Names.get(position));
        holder.address.setText(Addresses.get(position));
        //holder.rating.setText(ratings.get(position).toString());
        //TODO set the image from FireBase storage using the string link value
        //holder.image.setText(Names.get(position));
        //TODO on click listener
    }

    @Override
    public int getItemCount() {
        return Names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Chip mChip;
        //LinearLayout mLinearLayout;

        TextView name;
        TextView address;
        TextView rating;
        CircleImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

             name = itemView.findViewById(R.id.NameTextView);
            address = itemView.findViewById(R.id.AdressTextView);
            rating = itemView.findViewById(R.id.RatingTextView);
             image = itemView.findViewById(R.id.CircleImage);

        }
    }
}
