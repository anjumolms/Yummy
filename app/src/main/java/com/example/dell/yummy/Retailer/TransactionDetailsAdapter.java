package com.example.dell.yummy.Retailer;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.R;
import com.example.dell.yummy.model.Order;

import java.util.List;

class TransactionDetailsAdapter extends
        RecyclerView.Adapter<TransactionDetailsAdapter.TransactionViewHolder> {

    private Context mCtx;
    private List<Order> transactionDetailsList;
    private Order transactionDetails;
    IRetailerFragmentListener retailerFragmentListener;
    private boolean isConfirmOrderPage;


    public TransactionDetailsAdapter(Context mCtx,
                                     List<Order> transactionDetailsList,
                                     IRetailerFragmentListener retailerFragmentListener) {
        this.mCtx = mCtx;
        this.transactionDetailsList = transactionDetailsList;
        this.retailerFragmentListener = retailerFragmentListener;
    }


    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        if (transactionDetailsList != null) {

            transactionDetails = transactionDetailsList.get(position);
            holder.textViewUserId.setText("" + transactionDetails.getOrder_id());
            holder.textViewTransactionId.setText("" + transactionDetails.getWallet_tran_id());
            holder.textViewAmount.setText("" + transactionDetails.getOrder_value());
            holder.date.setText("" + transactionDetails.getOrder_date());
            if (isConfirmOrderPage
                    && transactionDetails.getOrder_status().equalsIgnoreCase("ORDER_SUCCESSFULL")) {
                holder.textViewTransactionStatus.setText("ORDER SUCCESSFULL");
                holder.textViewTransactionStatus.setTextColor(Color.parseColor("#4CAF50"));
            } else if (transactionDetails.getOrder_status().equalsIgnoreCase("ORDER_SUCCESSFULL")) {
                holder.textViewTransactionStatus.setText("ORDER PENDING");
                holder.textViewTransactionStatus.setTextColor(Color.parseColor("#FFC107"));
            } else {
                holder.textViewTransactionStatus.setText("ORDER FAILED");
                holder.textViewTransactionStatus.setTextColor(Color.parseColor("#F44336"));
            }
        }
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_transaction_details, parent,
                false);
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

    public void isRequestFromConfirmOrderFragment(boolean isConfirmOrderPage) {
        this.isConfirmOrderPage = isConfirmOrderPage;
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView textViewUserId, textViewTransactionId, textViewAmount,
                textViewTransactionStatus;
        CardView cardView;
        TextView date;

        public TransactionViewHolder(View itemView) {
            super(itemView);

            textViewUserId = itemView.findViewById(R.id.tv_user_id);
            textViewTransactionId = itemView.findViewById(R.id.tv_transaction_id);
            textViewAmount = itemView.findViewById(R.id.tv_amount);
            textViewTransactionStatus = itemView.findViewById(R.id.tv_transaction_status);
            date = itemView.findViewById(R.id.tv_date_id);
            cardView = itemView.findViewById(R.id.cv_transaction_details);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mCtx, "success", Toast.LENGTH_SHORT).show();
                    if (transactionDetailsList != null) {
                        if (!isConfirmOrderPage) {
                            int pos = getAdapterPosition();
                            if (retailerFragmentListener != null) {
                                retailerFragmentListener.loadEachTransactionFragment(transactionDetailsList.get(pos));
                            }
                        }

                    }
                }
            });
        }
    }
}





