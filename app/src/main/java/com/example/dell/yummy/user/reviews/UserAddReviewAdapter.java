package com.example.dell.yummy.user.reviews;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.user.store.ConfirmationAdapter;
import com.example.dell.yummy.webservice.DishesDetails;

import java.util.List;

public class UserAddReviewAdapter
        extends RecyclerView.Adapter<UserAddReviewAdapter.ReviewViewHolder> {

    private Context mCtx;
    private List<UserReview> userReviews;
    IFragmentListener iFragmentListener;


    public UserAddReviewAdapter(Context mCtx, List<UserReview> userReviews,
                                IFragmentListener mIFragmentListener) {
        this.mCtx = mCtx;
        this.userReviews = userReviews;
        iFragmentListener = mIFragmentListener;
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
        //getting the product of the specified position


        //binding the data with the viewholder views
        holder.storeName.setText(userReviews.get(position).getStoreName());
        holder.itemName.setText(userReviews.get(position).getOrderItem());


    }

    @Override
    public int getItemCount() {
        return userReviews.size();
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
                    if (iFragmentListener != null) {
                        iFragmentListener.showDialog();
                    }
                }
            });
        }
    }
}
