package com.example.dell.yummy.user.reviews;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserReviewFragment extends Fragment {
    IFragmentListener iFragmentListener;
    RecyclerView recyclerView1,recyclerView2;


    public UserReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_review,
         container, false);

        recyclerView1 = view.findViewById(R.id.rv_addreview);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView2 = view.findViewById(R.id.rv_viewreview);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));


//        if(iFragmentListener != null) {
//            iFragmentListener.showDialog();
//        }


        return view;
    }


    public void addListener(IFragmentListener iFragmentListener) {
        this.iFragmentListener = iFragmentListener;
    }
}
