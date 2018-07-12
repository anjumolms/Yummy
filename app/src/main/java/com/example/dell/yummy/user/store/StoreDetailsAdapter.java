package com.example.dell.yummy.user.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.R;
import com.example.dell.yummy.user.IUserViewListener;
import com.example.dell.yummy.user.dishes.DishesDetails;
import com.example.dell.yummy.user.dishes.UserDishesAdapter;

import java.util.List;

public class StoreDetailsAdapter extends RecyclerView.Adapter<StoreDetailsAdapter.DishesViewHolder> {

    private Context mCtx;

    private List<DishesDetails> dishesDetailsList;

    public StoreDetailsAdapter(Context mCtx, List<DishesDetails> dishesDetailsList) {
        this.mCtx = mCtx;
        this.dishesDetailsList = dishesDetailsList;
    }

    @Override
    public StoreDetailsAdapter.DishesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_store_details, parent,false);
        return new StoreDetailsAdapter.DishesViewHolder(view);
    }
    @Override
    public void onBindViewHolder(StoreDetailsAdapter.DishesViewHolder holder, int position) {
        //getting the product of the specified position
        DishesDetails dishesDetails = dishesDetailsList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(dishesDetails.getTitle());
        holder.textViewPrice.setText(String.valueOf(dishesDetails.getPrice()));
        }

    @Override
    public int getItemCount() {
        return dishesDetailsList.size();
    }


    class DishesViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle,textViewPrice;


        public DishesViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.dish_item_name);
            textViewPrice = itemView.findViewById(R.id.dish_item_price);

            textViewTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mCtx,"success",Toast.LENGTH_SHORT).show();

                }
            });

        }
    }


}
