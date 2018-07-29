package com.example.dell.yummy.user.reviews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.yummy.R;
import com.example.dell.yummy.user.store.ConfirmationAdapter;
import com.example.dell.yummy.webservice.DishesDetails;

import java.util.List;

public class UserAddReviewAdapter extends RecyclerView.Adapter<UserAddReviewAdapter.ReviewViewHolder> {

    private Context mCtx;
    private List<DishesDetails> dishesDetailsList;
    TextView textView;

    public UserAddReviewAdapter(Context mCtx, List<DishesDetails> dishesDetailsList) {
        this.mCtx = mCtx;
        this.dishesDetailsList = dishesDetailsList;
    }

    @Override
    public ConfirmationAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.add_review, parent,false);
        return new ConfirmationAdapter.ReviewViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ConfirmationAdapter.ReviewViewHolder holder, int position) {
        //getting the product of the specified position



            //binding the data with the viewholder views
            holder.textView.setText(dishesDetails.getItemName());

        }

    }

