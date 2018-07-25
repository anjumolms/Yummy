package com.example.dell.yummy.user.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dell.yummy.R;
import com.example.dell.yummy.webservice.DishesDetails;

import java.util.List;

public class StoreDetailsAdapter extends RecyclerView.Adapter<StoreDetailsAdapter.DishesViewHolder> {

    private Context mCtx;
    private List<DishesDetails> dishesDetailsList;
    int counter;

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
        holder.textViewTitle.setText(dishesDetails.getItemName());
        holder.textViewPrice.setText(String.valueOf(dishesDetails.getItemPrice()));
        holder. textViewCount.setText(String.valueOf(counter));

        }

    @Override
    public int getItemCount() {
        return dishesDetailsList.size();
    }

    public List<DishesDetails> getDishesDetailsList() {
        return dishesDetailsList;
    }

    class DishesViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle,textViewPrice,textViewCount;
        ImageButton addimage,removeimage;


        public DishesViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.dish_item_name);
            textViewPrice = itemView.findViewById(R.id.dish_item_price);
            addimage = itemView.findViewById(R.id.im_add);
            removeimage = itemView.findViewById(R.id.im_rem);
            textViewCount = itemView.findViewById(R.id.tv_dish_item_count);



            addimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position;
                    position = getAdapterPosition();
                    counter = dishesDetailsList.get(position).getCounter();
                    counter++;
                    dishesDetailsList.get(position).setCounter(counter);
                    textViewCount.setText(String.valueOf(counter));
                    }
            });

            removeimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position;
                    position = getAdapterPosition();
                    counter = dishesDetailsList.get(position).getCounter();
                    if(counter>0) {
                        counter--;
                    }
                    dishesDetailsList.get(position).setCounter(counter);
                    textViewCount.setText(String.valueOf(counter));
                    }
            });



        }
    }


}
