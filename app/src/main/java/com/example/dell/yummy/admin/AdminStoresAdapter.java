package com.example.dell.yummy.admin;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.StoreDetails;
import java.util.List;

public class AdminStoresAdapter extends
        RecyclerView.Adapter<AdminStoresAdapter.StoreViewHolder> {

    private Context mCtx;

    private List<StoreDetails> storeList;

    IAdminFragmentListener iAdminFragmentListener;

    public AdminStoresAdapter(Context mCtx, List<StoreDetails> productList,
                              IAdminFragmentListener iAdminFragmentListener) {
        this.mCtx = mCtx;
        this.storeList = productList;
        this.iAdminFragmentListener = iAdminFragmentListener;

    }

    public void setData(List<StoreDetails> data) {
        storeList = data;
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_stores, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, int position) {
        //getting the product of the specified position
        if (storeList != null) {
            StoreDetails storeDetails = storeList.get(position);
            holder.wallet.setVisibility(View.VISIBLE);
            //binding the data with the viewholder views
            holder.textViewStoreName.setText(storeDetails.getRetailName());
            holder.textViewStoreId.setText("Store ID  " + storeDetails.getRetailNumber());
            holder.wallet.setText("Wallet " + storeDetails.getRetailWallet());


        }
    }

    @Override
    public int getItemCount() {
        if (storeList != null) {
            return storeList.size();
        } else {
            return 0;
        }
    }

    class StoreViewHolder extends RecyclerView.ViewHolder {

        TextView textViewStoreName, textViewStoreId,wallet;
        CardView cardView;
        ImageView storeImg;


        public StoreViewHolder(View itemView) {
            super(itemView);

            textViewStoreName = itemView.findViewById(R.id.textViewStoreName);
            textViewStoreId = itemView.findViewById(R.id.textViewStoreId);
            cardView = itemView.findViewById(R.id.cv_storedetails);
            storeImg = itemView.findViewById(R.id.iv_store);

            wallet = itemView.findViewById(R.id.wallet_info);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mCtx, "success", Toast.LENGTH_SHORT).show();
                    if (iAdminFragmentListener != null) {
                        if (storeList != null) {

                            int userClickPosition = getAdapterPosition();
                            StoreDetails storeDetails = storeList.get(userClickPosition);
                            iAdminFragmentListener
                                    .passStoreDetails(Constants.SCREEN_ADMIN_TRANSACTION_DETAILS,
                                    storeDetails);
                        }
                    }

                }
            });

        }
    }


}

