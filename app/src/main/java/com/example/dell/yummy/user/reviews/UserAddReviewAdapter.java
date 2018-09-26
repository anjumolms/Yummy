package com.example.dell.yummy.user.reviews;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.yummy.user.IUserFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.UserReview;

import java.util.List;

public class UserAddReviewAdapter
        extends RecyclerView.Adapter<UserAddReviewAdapter.ReviewViewHolder> {

    private Context mCtx;
    private List<UserReview> userReviews;
    IUserFragmentListener iUserFragmentListener;


    public UserAddReviewAdapter(Context mCtx, List<UserReview> userReviews,
                                IUserFragmentListener mIUserFragmentListener) {
        this.mCtx = mCtx;
        this.userReviews = userReviews;
        iUserFragmentListener = mIUserFragmentListener;
    }

    @Override
    public UserAddReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.add_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAddReviewAdapter.ReviewViewHolder holder, int position) {
        if (userReviews != null) {
            holder.storeName.setText(" " + userReviews.get(position).getRetailNumer());
            holder.itemName.setText(userReviews.get(position).getOrderItem());
        }

    }

    @Override
    public int getItemCount() {
        if (userReviews != null) {
            return userReviews.size();
        } else {
            return 0;
        }

    }

    public void setData(List<UserReview> userReviewDetails) {
       userReviews = userReviewDetails;
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView storeName, itemName;
        CardView cardView;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.add_review_store_name);

            itemName = itemView.findViewById(R.id.add_review_item_name);
            cardView = itemView.findViewById(R.id.add_review_card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (userReviews != null) {
                        if (iUserFragmentListener != null) {
                            int userClickPosition = getAdapterPosition();
                            iUserFragmentListener
                                    .showDialog(userReviews.get(userClickPosition));
                        }
                    }
                }
            });
        }
    }
}
