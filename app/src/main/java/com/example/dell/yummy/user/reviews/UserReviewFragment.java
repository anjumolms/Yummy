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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserReviewFragment extends Fragment {

    RecyclerView recyclerView1;
    List<UserReview> userReviews;
    IFragmentListener mIFragmentListener;


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
        userReviews = new ArrayList<>();
        UserReview userReview1 = new UserReview();
        userReview1.setOrderItem("Chicken Biriyani");
        UserReview userReview4 = new UserReview();
        userReview4.setOrderItem("Kutthuportae");
        UserReview userReview2 = new UserReview();
        userReview2.setOrderItem("mutton biriyani");
        UserReview userReview3 = new UserReview();
        userReview3.setOrderItem("Meals");

        userReview1.setStoreName("Kantharies");
        userReview2.setStoreName("Gondola");
        userReview3.setStoreName("Chickfila");
        userReview4.setStoreName("thedln");

        userReviews.add(userReview1);
        userReviews.add(userReview2);
        userReviews.add(userReview3);
        userReviews.add(userReview4);


        UserAddReviewAdapter adapter = new UserAddReviewAdapter(getActivity(),
                userReviews, mIFragmentListener);
        recyclerView1.setAdapter(adapter);
        return view;
    }


    public void addListener(IFragmentListener miFragmentListener) {
        this.mIFragmentListener =  miFragmentListener;

    }
}
