package com.example.dell.yummy.user.reviews;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.UserReview;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserReviewFragment extends Fragment {

    RecyclerView mRecyclerView;
    List<UserReview> mListuserReviews;
    IFragmentListener mIFragmentListener;


    public UserReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_review,
                container, false);
        initViews(view);

        mListuserReviews = new ArrayList<>();
        UserReview userReview1 = new UserReview();
        userReview1.setOrderItem("Chicken Biriyani");
        UserReview userReview4 = new UserReview();
        userReview4.setOrderItem("Kutthuportae");
        UserReview userReview2 = new UserReview();
        userReview2.setOrderItem("mutton biriyani");
        UserReview userReview3 = new UserReview();
        userReview3.setOrderItem("Meals");

        UserReview userReview5 = new UserReview();
        userReview5.setOrderItem("Biriyani");
        UserReview userReview6 = new UserReview();
        userReview6.setOrderItem("Chappathi");
        UserReview userReview7 = new UserReview();
        userReview7.setOrderItem("Tea");
        UserReview userReview8 = new UserReview();
        userReview8.setOrderItem("Coffee");
        UserReview userReview9 = new UserReview();
        userReview9.setOrderItem("Meals");

        userReview1.setStoreName("Kantharies");
        userReview2.setStoreName("Gondola");
        userReview3.setStoreName("Chickfila");
        userReview4.setStoreName("thedln");
        userReview5.setStoreName("Le Arabia");
        userReview6.setStoreName("Ajwa");
        userReview7.setStoreName("Saravana");
        userReview8.setStoreName("Arayas");
        userReview9.setStoreName("thedln");

        mListuserReviews.add(userReview1);
        mListuserReviews.add(userReview2);
        mListuserReviews.add(userReview3);
        mListuserReviews.add(userReview4);
        mListuserReviews.add(userReview5);
        mListuserReviews.add(userReview6);
        mListuserReviews.add(userReview7);
        mListuserReviews.add(userReview8);
        mListuserReviews.add(userReview9);


        UserAddReviewAdapter adapter = new UserAddReviewAdapter(getActivity(),
                mListuserReviews, mIFragmentListener);
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_addreview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public void addListener(IFragmentListener miFragmentListener) {
        this.mIFragmentListener = miFragmentListener;

    }
}
