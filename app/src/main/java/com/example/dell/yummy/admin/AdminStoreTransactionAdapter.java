package com.example.dell.yummy.admin;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.R;
import com.example.dell.yummy.model.TransactionDetails;

import java.util.List;

public class AdminStoreTransactionAdapter extends
        RecyclerView.Adapter<AdminStoreTransactionAdapter.TransactionViewHolder>{
    private Context mCtx;
    private List<TransactionDetails> transactionDetailsList;
    private TransactionDetails transactionDetails;
    IAdminFragmentListener iAdminFragmentListener;
    private boolean isConfirmOrderPage;

    public AdminStoreTransactionAdapter(Context mCtx,
                                     List<TransactionDetails> transactionDetailsList,
                                     IAdminFragmentListener iAdminFragmentListener) {
        this.mCtx = mCtx;
        this.transactionDetailsList = transactionDetailsList;
        this.iAdminFragmentListener = iAdminFragmentListener;
    }


    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        if (transactionDetailsList != null) {

            transactionDetails = transactionDetailsList.get(position);
            holder.textViewUserId.setText("" + transactionDetails.getUserId());
            holder.textViewTransactionId.setText("" + transactionDetails.getWalletTranId());
            holder.textViewAmount.setText("" + transactionDetails.getOrderValue());
            holder.textViewTransactionStatus.setText(transactionDetails.getOrderStatus());
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

    public void setData(List<TransactionDetails> transactionDetails) {
        this.transactionDetailsList = transactionDetails;
    }

    public void isRequestFromConfirmOrderFragment(boolean isConfirmOrderPage) {
        this.isConfirmOrderPage = isConfirmOrderPage;
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView textViewUserId, textViewTransactionId, textViewAmount,
                textViewTransactionStatus;
        CardView cardView;

        public TransactionViewHolder(View itemView) {
            super(itemView);

            textViewUserId = itemView.findViewById(R.id.tv_user_id);
            textViewTransactionId = itemView.findViewById(R.id.tv_transaction_id);
            textViewAmount = itemView.findViewById(R.id.tv_amount);
            textViewTransactionStatus = itemView.findViewById(R.id.tv_transaction_status);
            cardView = itemView.findViewById(R.id.cv_transaction_details);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mCtx, "success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
