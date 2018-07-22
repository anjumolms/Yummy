package com.example.dell.yummy.user.store;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.R;
import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.webservice.StoreDetails;

import java.util.List;

public class UserStoresAdapter extends RecyclerView.Adapter<UserStoresAdapter.StoreViewHolder> {

    private Context mCtx;

    private List<StoreDetails> storeList;

    IFragmentListener miFragmentListener;

    public UserStoresAdapter(Context mCtx, List<StoreDetails> productList, IFragmentListener miFragmentListener) {
        this.mCtx = mCtx;
        this.storeList = productList;
        this.miFragmentListener = miFragmentListener;

    }
    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_stores,parent,false );
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, int position) {
        //getting the product of the specified position
        StoreDetails storeDetails = storeList.get(position);

        //binding the data with the viewholder views
        holder.textViewStoreName.setText(storeDetails.getRetailName());
        holder.textViewStoreId.setText("Store ID  "+storeDetails.getRetailNumber());
        }

    @Override
    public int getItemCount() {
        return storeList.size();
    }
    class StoreViewHolder extends RecyclerView.ViewHolder {

        TextView textViewStoreName, textViewStoreId;
        CardView cardView;


        public StoreViewHolder(View itemView) {
            super(itemView);

            textViewStoreName = itemView.findViewById(R.id.textViewStoreName);
            textViewStoreId = itemView.findViewById(R.id.textViewStoreId);
            cardView = itemView.findViewById(R.id.cv_storedetails);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mCtx,"success",Toast.LENGTH_SHORT).show();
                    if(miFragmentListener != null){
                        int userClickPosition = getAdapterPosition();
                        StoreDetails storeDetails = storeList.get(userClickPosition);
                        miFragmentListener.passStoreDetails(Constants.SCREEN_STORE_DETAILS,storeDetails);
                    }

                }
            });

        }
    }
}
