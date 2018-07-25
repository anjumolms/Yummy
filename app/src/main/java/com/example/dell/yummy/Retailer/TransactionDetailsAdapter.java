package com.example.dell.yummy.Retailer;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.webservice.TransactionDetails;

import java.util.List;

class TransactionDetailsAdapter extends RecyclerView.Adapter<TransactionDetailsAdapter.TransactionViewHolder>

{

    private Context mCtx;
    private List<TransactionDetails> transactionDetailsList;
    private TransactionDetails transactionDetails;
    IFragmentListener iFragmentListener;

    public TransactionDetailsAdapter(Context mCtx, List<TransactionDetails> transactionDetailsList,IFragmentListener iFragmentListener) {
        this.mCtx = mCtx;
        this.transactionDetailsList = transactionDetailsList;
        this.iFragmentListener = iFragmentListener;
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
        holder.textViewUserId.setText("User Id "+transactionDetails.getUserId());
        holder.textViewTransactionId.setText("Transaction Id "+transactionDetails.getWalletTranId());
        holder.textViewAmount.setText("Amount "+transactionDetails.getOrderValue());
        holder.textViewTransactionStatus.setText("Status "+transactionDetails.getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return transactionDetailsList.size();

     }

    class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView textViewUserId, textViewTransactionId, textViewAmount, textViewTransactionStatus;
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
                    if(iFragmentListener != null){
                        iFragmentListener.addFragment(Constants.SCREEN_RETAILER_EACH_TRANSACTION_DETAILS);
                    }

                }
            });
        }
    }
}





