package capstone.com.doctorfinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hsalf.smilerating.SmileRating;
import java.util.ArrayList;


/**
 * Created by amr on 5/8/18.
 */

public class CommentsRVAdapter extends RecyclerView.Adapter<CommentsRVAdapter.ViewHolder> {

    //private ArrayList<String> Images = new ArrayList<>();
    private ArrayList<String> Names = new ArrayList<>();
    private ArrayList<Double> ratings = new ArrayList<>();
    private ArrayList<String> Comments = new ArrayList<>();

    private Context mContext;

    public CommentsRVAdapter(Context mContext,ArrayList<String> names,ArrayList<Double> ratings, ArrayList<String> comments) {
       // Images = images;
        Names = names;
        Comments = comments;
        this.ratings = ratings;
        this.mContext = mContext;
    }
    @Override
    public CommentsRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_review_holder, parent, false);
        CommentsRVAdapter.ViewHolder holder = new CommentsRVAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommentsRVAdapter.ViewHolder holder, int position) {


        //Glide.with(mContext).load(Images.get(position)).into(holder.image);
        holder.name.setText(Names.get(position));
        //TODO set the rating in the smileyView here
        //holder.rating.setSelectedSmile(ratings.get(position).in);
        holder.comment.setText(Comments.get(position));




    }

    @Override
    public int getItemCount() {
        return Names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //CircleImageView image;
        TextView name;
        SmileRating rating;

        TextView comment;


        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.CNameTextView);
            rating = itemView.findViewById(R.id.Csmile_rating);
            rating.setClickable(false);
           // image = itemView.findViewById(R.id.CCircleImage);
            comment = itemView.findViewById(R.id.CCommentTextView);
        }
    }
}
