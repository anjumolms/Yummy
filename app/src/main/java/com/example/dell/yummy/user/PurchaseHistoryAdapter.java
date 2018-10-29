package com.example.dell.yummy.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.yummy.R;
import com.example.dell.yummy.model.Order;

import java.util.List;

public class PurchaseHistoryAdapter extends
        RecyclerView.Adapter<PurchaseHistoryAdapter.TransactionViewHolder> {
    private Context mCtx;
    private List<Order> transactionDetailsList;
    private Order transactionDetails;
    IUserFragmentListener iUserFragmentListener;
    private boolean isConfirmOrderPage;

    public PurchaseHistoryAdapter(Context mCtx,
                                  List<Order> transactionDetailsList,
                                  IUserFragmentListener iAdminFragmentListener) {
        this.mCtx = mCtx;
        this.transactionDetailsList = transactionDetailsList;
        this.iUserFragmentListener = iAdminFragmentListener;
    }


    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        if (transactionDetailsList != null) {

            transactionDetails = transactionDetailsList.get(position);
            holder.storeName.setText("" + transactionDetails.getRetail_name());
            holder.orderStatus.setText("" + transactionDetails.getOrder_status());
            holder.orderDate.setText("" + transactionDetails.getOrder_date());
            holder.total.setText("Total ₹ " + transactionDetails.getOrder_value());
            holder.orderId.setText("Order Id " + transactionDetails.getOrder_id());
        }
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_purchase_history_parent,
                parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (transactionDetailsList != null) {

            return transactionDetailsList.size();
        } else {
            return 0;
        }

    }

    public void setData(List<Order> transactionDetails) {
        this.transactionDetailsList = transactionDetails;
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {

        public TextView storeName;
        public TextView orderStatus;
        public TextView orderDate;
        public TextView total;
        public TextView orderId;
        LinearLayout linearLayout;

        public TransactionViewHolder(View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.purchase_history_store_name);
            orderStatus = itemView.findViewById(R.id.purchase_history_order_status);
            orderDate = itemView.findViewById(R.id.purchase_history_order_date);
            total = itemView.findViewById(R.id.purchase_history_total);
            orderId = itemView.findViewById(R.id.purchase_history_order_id);
            linearLayout = itemView.findViewById(R.id.purchase_history_ll);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                if(iUserFragmentListener != null && transactionDetailsList != null){
                    iUserFragmentListener.showPurchaseHistory(transactionDetailsList.get(pos));
                }
                }
            });
        }
    }
}
