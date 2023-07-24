package com.example.testprojectgithub.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testprojectgithub.R;
import com.example.testprojectgithub.model.Review;
import com.example.testprojectgithub.model.User;
import com.example.testprojectgithub.remote.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews;
    private UserService userService;
    private String apiKey;

    public ReviewAdapter(List<Review> reviews, UserService userService, String apiKey) {
        this.reviews = reviews;
        this.userService = userService;
        this.apiKey = apiKey;
    }


    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewUsername;
        private TextView textViewRating;
        private TextView textViewComment;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewComment = itemView.findViewById(R.id.textViewComment);
        }

        public void bind(Review review) {
            String userId = String.valueOf(review.getUserId());


            fetchUsername(apiKey, userId);

            String ratingText = "Rating: " + review.getRating();
            textViewRating.setText(ratingText);
            textViewComment.setText(review.getComment());
        }

        private void fetchUsername(String apiKey, String userId) {


            Call<User> call = userService.getUser(apiKey, userId);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User u = response.body();
                        if (u != null) {
                            String username = u.getUsername();
                            textViewUsername.setText(username);
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // Handle failure
                }
            });
        }
    }
}

