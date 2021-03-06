package com.example.dell.yummy.user.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.yummy.R;
import com.example.dell.yummy.model.DishesDetails;

import java.util.List;

public class ConfirmationAdapter extends RecyclerView.Adapter<ConfirmationAdapter.DishesViewHolder>{
    private Context mCtx;
    private List<DishesDetails> dishesDetailsList;

    public ConfirmationAdapter(Context mCtx, List<DishesDetails> dishesDetailsList) {
        this.mCtx = mCtx;
        this.dishesDetailsList = dishesDetailsList;
    }

    @Override
    public ConfirmationAdapter.DishesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_confirmation, parent,false);
        return new ConfirmationAdapter.DishesViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ConfirmationAdapter.DishesViewHolder holder, int position) {
        //getting the product of the specified position
        DishesDetails dishesDetails = dishesDetailsList.get(position);
        if(dishesDetails.getCounter()>0) {
            int amnt=dishesDetails.getItemPrice()*dishesDetails.getCounter();
            //binding the data with the viewholder views
            holder.textViewTitle.setText(dishesDetails.getItemName());
            holder.textViewPrice.setText(String.valueOf(amnt));
            holder.textViewQty.setText(String.valueOf(dishesDetails.getCounter()));
        }

    }

    @Override
    public int getItemCount() {
        return dishesDetailsList.size();
    }

    public List<DishesDetails> getDishesDetailsList() {
        return dishesDetailsList;
    }

    class DishesViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle,textViewPrice,textViewQty;

        public DishesViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.conf_order_item);
            textViewPrice = itemView.findViewById(R.id.conf_order_cost);
            textViewQty = itemView.findViewById(R.id.conf_order_qty);
        }
    }

  }
