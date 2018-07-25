package com.example.dell.yummy.user.store;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.user.UserHomeActivity;
import com.example.dell.yummy.webservice.DishesDetails;
import com.example.dell.yummy.webservice.IApiInterface;
import com.example.dell.yummy.webservice.TransactionDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.dell.yummy.user.UserHomeActivity.userid;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment {

    IFragmentListener iFragmentListener;
    List<DishesDetails> dishesDetails;
    ConfirmationAdapter confirmationAdapter;
    RecyclerView recyclerView;
    TextView textViewTotal;
    private int total=0;
    private double ordervalue = 0;
    private int orderitemcount = 0;
    Button confirmbutton;
    private int userid;
    String menuitem = "";
    private int retailid;
    public ConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);
        recyclerView = view.findViewById(R.id.rv_confirmation_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        textViewTotal=view.findViewById(R.id.textView5);
        confirmbutton = view.findViewById(R.id.bt_confirm);
        if (dishesDetails != null) {

            confirmationAdapter = new ConfirmationAdapter(getActivity(), dishesDetails);
            //setting adapter to recyclerview
            recyclerView.setAdapter(confirmationAdapter);

            for (DishesDetails dishdetail:dishesDetails) {
                if(dishdetail.getCounter()>0){
                    total=total+(dishdetail.getItemPrice()*dishdetail.getCounter());
                    orderitemcount = orderitemcount + dishdetail.getCounter();
                    menuitem = menuitem +","+dishdetail.getMenuId();
                    retailid = dishdetail.getRetailId();
                    }
            }
            textViewTotal.setText("Total : "+total);

        }

        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userid = UserHomeActivity.userid;
                final TransactionDetails transactionDetails = new TransactionDetails();
                transactionDetails.setUserId(userid);
                transactionDetails.setRetailId(retailid);
                transactionDetails.setOrderItemCount(orderitemcount);
                transactionDetails.setOrderValue(total);
                transactionDetails.setOrderItemsString(menuitem);


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(IApiInterface.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                        .build();

                IApiInterface iApiInterface = retrofit.create(IApiInterface.class);
                Call<TransactionDetails> call = iApiInterface
                        .getOrderDetails(transactionDetails);//how to use userid defined in userhome ctivity here
                call.enqueue(new Callback<TransactionDetails>() {
                    @Override
                    public void onResponse(Call<TransactionDetails> call, Response<TransactionDetails> response) {
                        if (response != null)
                            if (response.code() == 200) {
                              TransactionDetails  transactionDetails = response.body();
                            } else {
                                Toast.makeText(getActivity(), response.code()
                                        + response.message(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    @Override
                    public void onFailure(Call<TransactionDetails> call, Throwable t) {
                        Toast.makeText(getActivity(),"invalid", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        return view;

    }

    public void addListener(IFragmentListener iFragmentListener) {
        this.iFragmentListener = iFragmentListener;
    }

    public void setConfirmationDetails(List<DishesDetails> dishesDetails) {
        this.dishesDetails=dishesDetails;
    }
}
