package com.example.dell.yummy.user.store;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.R;
import com.example.dell.yummy.user.IUserFragmentListener;
import com.example.dell.yummy.model.StoreDetails;

import java.util.ArrayList;
import java.util.List;

public class UserStoresAdapter extends
        RecyclerView.Adapter<UserStoresAdapter.StoreViewHolder> implements Filterable {

    private Context mCtx;

    private List<StoreDetails> storeList;
    private ArrayList<StoreDetails> mDisplayList;

    IUserFragmentListener miUserFragmentListener;

    public UserStoresAdapter(Context mCtx, List<StoreDetails> productList,
                             IUserFragmentListener miUserFragmentListener) {
        this.mCtx = mCtx;
        this.storeList = productList;
        this.miUserFragmentListener = miUserFragmentListener;

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
            //binding the data with the viewholder views
            holder.textViewStoreName.setText(storeDetails.getRetailName());
            holder.textViewStoreId.setText("Store ID  " + storeDetails.getRetailNumber());

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

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayList = (ArrayList<StoreDetails>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<StoreDetails> FilteredArrList = new ArrayList<StoreDetails>();

                if (storeList == null) {
                    storeList = new ArrayList<StoreDetails>(mDisplayList); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = storeList.size();
                    results.values = storeList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < storeList.size(); i++) {
                        String data = storeList.get(i).getRetailName();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            StoreDetails storeDetails = storeList.get(i);
                            FilteredArrList.add(storeDetails);
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    class StoreViewHolder extends RecyclerView.ViewHolder {

        TextView textViewStoreName, textViewStoreId;
        CardView cardView;
        ImageView storeImg;


        public StoreViewHolder(View itemView) {
            super(itemView);

            textViewStoreName = itemView.findViewById(R.id.textViewStoreName);
            textViewStoreId = itemView.findViewById(R.id.textViewStoreId);
            cardView = itemView.findViewById(R.id.cv_storedetails);
            storeImg = itemView.findViewById(R.id.iv_store);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mCtx, "success", Toast.LENGTH_SHORT).show();
                    if (miUserFragmentListener != null) {
                        if (storeList != null) {

                            int userClickPosition = getAdapterPosition();
                            StoreDetails storeDetails = storeList.get(userClickPosition);
                            miUserFragmentListener.passStoreDetails(Constants.SCREEN_STORE_DETAILS, storeDetails);
                        }
                    }

                }
            });

        }
    }


}
