package com.example.dell.yummy.Retailer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.R;
import com.example.dell.yummy.model.DishesDetails;

import java.util.ArrayList;
import java.util.List;

class RetailerListItemAdapter extends
        RecyclerView.Adapter<RetailerListItemAdapter.ListItemViewHolder> {

    private Context mCtx;

    private List<DishesDetails> retailordishesList;
    private IRetailerFragmentListener mFragmentListener;
    private List<DishesDetails> deletedItemsList = new ArrayList<>();

    public RetailerListItemAdapter(Context mCtx, List<DishesDetails> retailordishesList,
                                   IRetailerFragmentListener mFragmentListener) {
        this.mCtx = mCtx;
        this.retailordishesList = retailordishesList;
        this.mFragmentListener = mFragmentListener;
    }

    public List<DishesDetails> getDeletedItemsList() {
        return deletedItemsList;
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
        if (retailordishesList != null) {

            DishesDetails dishesDetails = retailordishesList.get(position);
            holder.textViewTitle.setText(dishesDetails.getItemName());
            holder.textViewPrice.setText("" + dishesDetails.getItemPrice());
            holder.stock.setText(""+ dishesDetails.getItemStock());
            holder.checkBox.setChecked(false);
        }
    }


    @Override
    public int getItemCount() {
        if(retailordishesList!=null)
            return retailordishesList.size();
        else
            return 0;
    }

    public void setData(List<DishesDetails> retailerDishDetails) {
        retailordishesList = retailerDishDetails;
        deletedItemsList = null;
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewPrice,stock;
        ImageButton btnRetailerUpdate;
        CheckBox checkBox;


        public ListItemViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.tv_retailer_each_item);
            textViewPrice = itemView.findViewById(R.id.tv_retailer_listitem_cost);
            btnRetailerUpdate = itemView.findViewById(R.id.bt_retailer_update);
            checkBox = itemView.findViewById(R.id.checkbox_retailer);
            stock = itemView.findViewById(R.id.tv_retailer_listitem_stock);

            if (retailordishesList != null) {

                btnRetailerUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mFragmentListener != null) {
                            int pos = getAdapterPosition();
                            mFragmentListener.showItemUpdatePopup(retailordishesList.get(pos));
                        }
                        Toast.makeText(mCtx, "update popup", Toast.LENGTH_SHORT).show();

                    }
                });
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        if (checkBox.isChecked()) {

                            if (deletedItemsList == null) {
                                deletedItemsList = new ArrayList<>();
                            }
                            deletedItemsList.add(retailordishesList.get(pos));
                        } else {
                            if (deletedItemsList != null
                                    && deletedItemsList
                                    .contains(retailordishesList.get(pos))) {
                                deletedItemsList
                                        .remove(retailordishesList.get(pos));
                            }
                        }

                    }
                });
            }
        }
    }
}
