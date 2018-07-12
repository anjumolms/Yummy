package com.example.dell.yummy.user.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.R;
import com.example.dell.yummy.user.IUserViewListener;

import java.util.List;

public class UserStoresAdapter extends RecyclerView.Adapter<UserStoresAdapter.StoreViewHolder> {

    private Context mCtx;

    private List<StoreDetails> storeList;

    IUserViewListener miUserViewListener;

    public UserStoresAdapter(Context mCtx, List<StoreDetails> productList, IUserViewListener miUserViewListener) {
        this.mCtx = mCtx;
        this.storeList = productList;
        this.miUserViewListener = miUserViewListener;

    }
    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_stores, null);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, int position) {
        //getting the product of the specified position
        StoreDetails storeDetails = storeList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(storeDetails.getTitle());
        holder.textViewShortDesc.setText(storeDetails.getShortdesc());
        holder.textViewRating.setText(String.valueOf(storeDetails.getRating()));
        holder.textViewPrice.setText(String.valueOf(storeDetails.getPrice()));
        }

    @Override
    public int getItemCount() {
        return storeList.size();
    }
    class StoreViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;


        public StoreViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);

            textViewTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mCtx,"success",Toast.LENGTH_SHORT).show();
                    if(miUserViewListener != null){
                        miUserViewListener.addFragment(Constants.SCREEN_STORE_DETAILS);
                    }

                }
            });

        }
    }
}
