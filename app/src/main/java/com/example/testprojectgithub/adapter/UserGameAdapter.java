package com.example.testprojectgithub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testprojectgithub.R;
import com.example.testprojectgithub.model.Game;
import com.example.testprojectgithub.model.UserGame;

import java.util.List;

public class UserGameAdapter extends RecyclerView.Adapter<UserGameAdapter.ViewHolder> {

    /**
     * Create ViewHolder class to bind list item view
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        public TextView tvGameUserRating;
        public TextView tvGameUserReview;

        public ViewHolder(View itemView) {
            super(itemView);

            tvGameUserRating = (TextView) itemView.findViewById(R.id.tvGameUserRating);
            tvGameUserReview = (TextView) itemView.findViewById(R.id.tvGameUserReview);

            itemView.setOnLongClickListener(this);
        }
        @Override
        public boolean onLongClick(View view) {
            currentPos = getAdapterPosition(); //key point, record the position here
            return false;
        }
    }

    private List<UserGame> mListData;   // list of game objects
    private Context mContext;
    private int currentPos;// activity context

    public UserGameAdapter(Context context, List<UserGame> listData){
        mListData = listData;
        mContext = context;
    }

    private Context getmContext(){return mContext;}


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the single item layout
        View view = inflater.inflate(R.layout.user_game_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // bind data to the view holder
        UserGame m = mListData.get(position);
        holder.tvGameUserRating.setText(m.getGameUserRating());
        holder.tvGameUserReview.setText("Rating : " + m.getGameUserReview());
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public UserGame getSelectedItem(){
        if(currentPos>= 0 && mListData != null && currentPos<mListData.size()){
            return mListData.get(currentPos);
        }
        return null;
    }

}