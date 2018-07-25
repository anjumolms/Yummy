package com.example.dell.yummy.Retailer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.R;
import com.example.dell.yummy.webservice.DishesDetails;

import java.util.List;

class RetailerListItemAdapter extends RecyclerView.Adapter<RetailerListItemAdapter.ListItemViewHolder> {

    private Context mCtx;

    private List<DishesDetails> retailordishesList;

    public RetailerListItemAdapter(Context mCtx, List<DishesDetails> retailordishesList) {
        this.mCtx = mCtx;
        this.retailordishesList = retailordishesList;
    }

    @Override
    public RetailerListItemAdapter.ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_retailor_listitem_rv, parent, false);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        //getting the product of the specified position
        DishesDetails dishesDetails = retailordishesList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(dishesDetails.getItemName());
        holder.textViewPrice.setText("" + dishesDetails.getItemPrice());
    }


    @Override
    public int getItemCount() {
        return retailordishesList.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewPrice, btnRetailerUpdate, btnRetailerDelete;


        public ListItemViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.tv_retailer_each_item);
            textViewPrice = itemView.findViewById(R.id.tv_retailer_listitem_cost);
            btnRetailerUpdate = itemView.findViewById(R.id.bt_retailer_update);
            btnRetailerDelete = itemView.findViewById(R.id.bt_retailer_delete);

            btnRetailerUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mCtx, "update popup", Toast.LENGTH_SHORT).show();
//                    if (miFragmentListener != null) {
//                        miFragmentListener.addFragment(Constants.SCREEN_STORE_DETAILS);
//                    }

                }
            });
           btnRetailerDelete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Toast.makeText(mCtx, "update popup", Toast.LENGTH_SHORT).show();
               }
           });

        }
    }

}
