package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviememoir.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.MemoirDetailsResult;
import Model.MovieDetailsResult;

public class MemoirRecyclerAdapter extends RecyclerView.Adapter<MemoirRecyclerAdapter.ViewHolder> {

    private List<MemoirDetailsResult> memoirDetailsResultList;
    private RecyclerViewClickListener memoirRecyclerViewClickListener;

    public MemoirRecyclerAdapter(List<MemoirDetailsResult> memoirDetailsResultList, RecyclerViewClickListener recyclerViewClickListener) {
        super();
        this.memoirDetailsResultList = memoirDetailsResultList;
        this.memoirRecyclerViewClickListener = recyclerViewClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView memoirmovieName;
        public TextView memoirmovieReleaseDate;
        public ImageView memoirmovieImage;
        public TextView memoirmovieWatcheOnDate;
        public TextView memoirmovieCinemaPostCode;
        public TextView memoirmovieUserFeedback;
        public RatingBar memoirmovieRating;
        RecyclerViewClickListener recyclerViewClickListener;

        public ViewHolder(View itemView, RecyclerViewClickListener recyclerViewClickListener){
            super(itemView);
            memoirmovieName = itemView.findViewById(R.id.memoirList_movieName);
            memoirmovieImage = itemView.findViewById(R.id.memoirmovie_imageView);
            memoirmovieReleaseDate = itemView.findViewById(R.id.memoirList_movieReleaseDate_value);
            memoirmovieWatcheOnDate = itemView.findViewById(R.id.memoirList_movieWatchedOn_value);
            memoirmovieCinemaPostCode = itemView.findViewById(R.id.memoirList_movieCinemaPostCode);
            memoirmovieUserFeedback = itemView.findViewById(R.id.memoirList_movieMemory);
            memoirmovieRating = itemView.findViewById(R.id.ratingBar_movieRating);
            this.recyclerViewClickListener = recyclerViewClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            recyclerViewClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {
        void onItemClick(int item);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View memoirView = inflater.inflate(R.layout.recycler_view_memoir, parent, false);
        MemoirRecyclerAdapter.ViewHolder holder = new MemoirRecyclerAdapter.ViewHolder(memoirView, memoirRecyclerViewClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MemoirDetailsResult memoirAdapter = memoirDetailsResultList.get(position);
        holder.memoirmovieName.setText(memoirAdapter.getMemoirmovie_name());
        holder.memoirmovieReleaseDate.setText(memoirAdapter.getMemoirmovie_release_year());
        holder.memoirmovieWatcheOnDate.setText(memoirAdapter.getMemoirmovie_watchdate());
        holder.memoirmovieCinemaPostCode.setText(memoirAdapter.getMemoirmovie_cinemaPostcode());
        holder.memoirmovieUserFeedback.setText(memoirAdapter.getMemoirmovie_summary());
        holder.memoirmovieRating.setRating(Float.parseFloat(memoirAdapter.getMemoirmovie_rating()));
        String url = memoirAdapter.getMemoirimg_url();
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.moviep)
                .resize(200, 200)
                .centerInside()
                .into(((ViewHolder) holder).memoirmovieImage);
    }

    @Override
    public int getItemCount() {
        return memoirDetailsResultList.size();
    }
}
