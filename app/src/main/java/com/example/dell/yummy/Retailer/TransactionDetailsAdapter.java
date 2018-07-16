package com.example.dell.yummy.Retailer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.R;

import java.util.List;

class TransactionDetailsAdapter extends RecyclerView.Adapter<TransactionDetailsAdapter.TransactionViewHolder>

{

    private Context mCtx;
    private List<TransactionDetails> transactionDetailsList;
    private TransactionDetails transactionDetails;

    public TransactionDetailsAdapter(Context mCtx, List<TransactionDetails> transactionDetailsList) {
        this.mCtx = mCtx;
        this.transactionDetailsList = transactionDetailsList;
    }


    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_transaction_details, null);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        //getting the product of the specified position

        transactionDetails = transactionDetailsList.get(position);

        //binding the data with the viewholder views
        holder.textViewRetailerId.setText(" "+transactionDetails.getUserId());
        holder.textViewTransactionId.setText(""+transactionDetails.getTransactionId());
        holder.textViewAmount.setText(""+transactionDetails.getTotalAmount());
    }

    @Override
    public int getItemCount() {
        return transactionDetailsList.size();

     }

    class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView textViewRetailerId, textViewTransactionId, textViewAmount;

        public TransactionViewHolder(View itemView) {
            super(itemView);

            textViewRetailerId = itemView.findViewById(R.id.tv_retailer_id);
            textViewTransactionId = itemView.findViewById(R.id.tv_transaction_id);
            textViewAmount = itemView.findViewById(R.id.tv_amount);


            textViewRetailerId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mCtx, "success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}





