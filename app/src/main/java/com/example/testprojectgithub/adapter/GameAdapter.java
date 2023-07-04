package com.example.testprojectgithub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testprojectgithub.R;
import com.example.testprojectgithub.model.Game;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    /**
     * Create ViewHolder class to bind list item view
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        public TextView tvName;
        public TextView tvRating;
        public TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvRating = (TextView) itemView.findViewById(R.id.tvRating);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);

            itemView.setOnLongClickListener(this);
        }
        @Override
        public boolean onLongClick(View view) {
            currentPos = getAdapterPosition(); //key point, record the position here
            return false;
        }
    }

    private List<Game> mListData;   // list of game objects
    private Context mContext;
    private int currentPos;// activity context

    public GameAdapter(Context context, List<Game> listData){
        mListData = listData;
        mContext = context;
    }

    private Context getmContext(){return mContext;}


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the single item layout
        View view = inflater.inflate(R.layout.game_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // bind data to the view holder
        Game m = mListData.get(position);
        holder.tvName.setText(m.getGameName());
        holder.tvRating.setText("Rating : " + m.getGameRating());
        holder.tvDescription.setText(m.getGameDescription());
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public Game getSelectedItem(){
        if(currentPos>= 0 && mListData != null && currentPos<mListData.size()){
            return mListData.get(currentPos);
        }
        return null;
    }

}