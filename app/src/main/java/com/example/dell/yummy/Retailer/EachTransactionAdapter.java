package com.example.dell.yummy.Retailer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.yummy.R;
import com.example.dell.yummy.model.DishesDetails;

import java.util.List;

public class EachTransactionAdapter extends
        RecyclerView.Adapter<EachTransactionAdapter.TransactionViewHolder> {

    private Context mCtx;
    private List<DishesDetails> menuList;


    public EachTransactionAdapter(Context mCtx,
                                     List<DishesDetails> menuList) {
        this.mCtx = mCtx;
        this.menuList = menuList;
    }


    @Override
    public void onBindViewHolder(EachTransactionAdapter.TransactionViewHolder holder,
                                 int position) {
        if (menuList != null) {

           DishesDetails  details = menuList.get(position);

            holder.textDishName.setText("" + details.getItemName());
            holder.textDishCount.setText(" "+ details.getCounter());
        }
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.each_transaction_adapter_layout, parent,
                false);
        return new TransactionViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (menuList != null) {

            return menuList.size();
        } else {
            return 0;
        }

    }

    public void setData(List<DishesDetails> menuList) {
        this.menuList = menuList;
    }


    class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView textDishName, textDishCount;

        public TransactionViewHolder(View itemView) {
            super(itemView);

            textDishName = itemView.findViewById(R.id.retailer_order_item_name);
            textDishCount = itemView.findViewById(R.id.retailer_order_item_count);

        }
    }
}
