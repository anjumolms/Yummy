package com.example.dell.yummy.admin;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.example.dell.yummy.model.RetailerDetails;
import com.example.dell.yummy.model.StoreDetails;

import java.util.List;

public class AdminListStoreAdapter extends RecyclerView.Adapter<AdminListStoreAdapter.StoreViewHolder> {

    private Context mCtx;

    private List<RetailerDetails> retailerDetails;

    IAdminFragmentListener iAdminFragmentListener;

    public AdminListStoreAdapter(Context mCtx, List<RetailerDetails> retailerDetails,
                                 IAdminFragmentListener iAdminFragmentListener) {
        this.mCtx = mCtx;
        this.retailerDetails = retailerDetails;
        this.iAdminFragmentListener = iAdminFragmentListener;

    }

    public void setData(List<RetailerDetails> data) {
        retailerDetails = data;
    }

    @NonNull
    @Override
    public AdminListStoreAdapter.StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_admin_list_stores, parent,
                false);
        return new AdminListStoreAdapter.StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminListStoreAdapter.StoreViewHolder holder, int position) {
        //getting the product of the specified position
        if (retailerDetails != null) {

            RetailerDetails storeDetails = retailerDetails.get(position);
            //binding the data with the viewholder views
            holder.textViewStoreName.setText(storeDetails.getRetailName());
            holder.textViewStoreId.setText("" + storeDetails.getRetailId());
            holder.wallet.setText("" + storeDetails.getRetailWallet());
            holder.email.setText("" + storeDetails.getRetailEmail());
            holder.pocName.setText("" + storeDetails.getRetailPoc());
            holder.account.setText("" + storeDetails.getRetailAccount());
            holder.mobile.setText("" + storeDetails.getRetailPhone());
            holder.location.setText("" + storeDetails.getLocation_id());

        }
    }

    @Override
    public int getItemCount() {
        if (retailerDetails != null) {
            return retailerDetails.size();
        } else {
            return 0;
        }
    }

    class StoreViewHolder extends RecyclerView.ViewHolder {

        TextView textViewStoreName, textViewStoreId, wallet,
                email, pocName, account, mobile, location;

        public StoreViewHolder(View itemView) {
            super(itemView);

            textViewStoreName = itemView.findViewById(R.id.list_store_name);
            textViewStoreId = itemView.findViewById(R.id.list_store_id);
            wallet = itemView.findViewById(R.id.list_store_wallet_info);
            email = itemView.findViewById(R.id.admin_list_store_retail_email);
            pocName = itemView.findViewById(R.id.admin_store_list_retail_poc);
            account = itemView.findViewById(R.id.admin_store_list_retail_account);
            mobile = itemView.findViewById(R.id.admin_store_list_retail_mobile);
            location = itemView.findViewById(R.id.admin_store_list_retailer_location);

            mobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (retailerDetails != null
                            && retailerDetails.get(pos).getRetailPhone() != null) {

                        Intent intent = new Intent(Intent.ACTION_DIAL,
                                Uri.parse("tel:" + retailerDetails.get(pos).getRetailPhone()));
                        mCtx.startActivity(intent);
                    }
                }
            });

            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    if (retailerDetails != null
                            && retailerDetails.get(pos).getRetailEmail() != null) {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", retailerDetails.get(pos).getRetailEmail(), null));
                        mCtx.startActivity(Intent.createChooser(emailIntent, "Send email..."));
                    }
                }
            });
        }
    }

}
