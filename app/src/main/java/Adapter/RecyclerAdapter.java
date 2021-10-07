package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Model.MovieDetailsResult;
import com.example.mymoviememoir.R;
import com.squareup.picasso.Picasso;

public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.ViewHolder> {

    private List<MovieDetailsResult> movieDetailsResultsList;
    private RecyclerViewClickListener movierecyclerViewClickListener;

    public RecyclerAdapter(List<MovieDetailsResult> movieDetailsResultsList, RecyclerViewClickListener recyclerViewClickListener) {
        super();
        this.movieDetailsResultsList = movieDetailsResultsList;
        movierecyclerViewClickListener = recyclerViewClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView movienameTextView;
        public TextView moviereleaseTextView;
        public ImageView movieimageView;
        RecyclerViewClickListener recyclerViewClickListener;

        public ViewHolder(View itemView, RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);
            movienameTextView = itemView.findViewById(R.id.movie_name);
            moviereleaseTextView = itemView.findViewById(R.id.release_year);
            movieimageView = itemView.findViewById(R.id.movieImage);
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
    @Override
    public int getItemCount() {
        return movieDetailsResultsList.size();
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View moviesView = inflater.inflate(R.layout.recycler_view, parent, false);
        ViewHolder holder = new ViewHolder(moviesView, movierecyclerViewClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        MovieDetailsResult movieAdapter = movieDetailsResultsList.get(position);
        holder.movienameTextView.setText(movieAdapter.getMovie_name());
        holder.moviereleaseTextView.setText(movieAdapter.getMovie_release_year());
        String url= movieAdapter.getImg_url();
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.moviep)
                .resize(200, 200)
                .centerInside()
                .into(((ViewHolder) holder).movieimageView);
    }

}


