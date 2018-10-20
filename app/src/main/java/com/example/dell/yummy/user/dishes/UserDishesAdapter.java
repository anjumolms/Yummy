package com.example.dell.yummy.user.dishes;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.R;
import com.example.dell.yummy.user.IUserFragmentListener;
import com.example.dell.yummy.model.DishesDetails;

import java.util.List;

public class UserDishesAdapter extends
        RecyclerView.Adapter<UserDishesAdapter.DishesViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    IUserFragmentListener miUserFragmentListener;
    //we are storing all the products in a list
    private List<DishesDetails> dishesDetailsList;


    //getting the context and product list with constructor
    public UserDishesAdapter(Context mCtx, List<DishesDetails> dishesDetailsList,
                             IUserFragmentListener miUserFragmentListener) {
        this.mCtx = mCtx;
        this.dishesDetailsList = dishesDetailsList;
        this.miUserFragmentListener = miUserFragmentListener;
    }

    public void setData(List<DishesDetails> data) {
        dishesDetailsList = data;
    }

    @Override
    public DishesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_dishes, parent, false);
        return new DishesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DishesViewHolder holder, int position) {
        //getting the product of the specified position
        if (dishesDetailsList != null) {
            DishesDetails dishesDetails = dishesDetailsList.get(position);

            //binding the data with the viewholder views
            holder.textViewTitle.setText(dishesDetails.getItemName());
            holder.textViewPrice.setText("₹ " + dishesDetails.getItemPrice());
            holder.ratingBar.setRating(dishesDetails.getReview());
            holder.stock.setText("Stock    " + dishesDetails.getItemStock());
            holder.storeId.setText(dishesDetails.getRetail_name());

            if (dishesDetails.getItemSignature() == 1) {
                holder.textViewTitle.setTextColor(Color.parseColor("#E91E63"));
            } else {
                holder.textViewTitle.setTextColor(Color.parseColor("#000000"));
            }

            if(dishesDetails.getMenuType() == 2){
                holder.textViewTitle
                        .setCompoundDrawablesWithIntrinsicBounds(R.drawable.veg_symbol,
                                0, 0, 0);
            }
        }

    }


    @Override
    public int getItemCount() {
        if (dishesDetailsList != null) {
            return dishesDetailsList.size();
        } else {
            return 0;
        }

    }


    class DishesViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewPrice,stock,storeId;
        CardView cardView;
        ImageView storeImg;
        RatingBar ratingBar;


        public DishesViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            cardView = itemView.findViewById(R.id.cv_disheitem);
            storeImg = itemView.findViewById(R.id.iv_dish);
            ratingBar = itemView.findViewById(R.id.rtbProductRating);
            stock = itemView.findViewById(R.id.dish_item_stock);
            storeId = itemView.findViewById(R.id.tv_dish_retailer_id);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mCtx, "success", Toast.LENGTH_SHORT).show();
                    if (miUserFragmentListener != null) {
                        if (dishesDetailsList != null) {
                            int clickPosition = getAdapterPosition();
                            DishesDetails dishesDetails = dishesDetailsList.get(clickPosition);
                            miUserFragmentListener.addPopup(dishesDetails);
                        }

                    }
                }
            });

        }
    }
}