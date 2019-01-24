package com.example.dell.yummy.user;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.yummy.R;
import com.example.dell.yummy.user.dishes.UserDishesFragment;
import com.example.dell.yummy.user.reviews.UserReviewFragment;
import com.example.dell.yummy.user.store.UserStoresFragment;
import com.example.dell.yummy.model.StoreDetails;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserViewPagerFragment extends Fragment implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private UserStoresFragment mUserStoresFragment;
    private UserDishesFragment mUserDishesFragment;
    private UserReviewFragment mUserReviewFragment;
    private IUserFragmentListener miUserFragmentListener;
    private TextView mTextView;

    public UserViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_user_view_pager, container,
               false);
        initFragments();
        viewPager =  view.findViewById(R.id.viewpager);
        mTextView = view.findViewById(R.id.tv_nav_icon);
        addTabs(viewPager);
        tabLayout =  view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        mTextView.setOnClickListener(this);
        return view;

    }

    private void initFragments() {

        mUserStoresFragment = new UserStoresFragment();
        mUserStoresFragment.addListener(miUserFragmentListener);

        mUserDishesFragment = new UserDishesFragment();
        mUserDishesFragment.addListener(miUserFragmentListener);

        mUserReviewFragment = new UserReviewFragment();
        mUserReviewFragment.addListener(miUserFragmentListener);
    }

    private void addTabs(ViewPager viewPager) {
        FragmentUserPagerAdapter adapter = new FragmentUserPagerAdapter(getActivity()
                .getSupportFragmentManager());
        adapter.addFrag(mUserStoresFragment , "Stores");
        adapter.addFrag(mUserDishesFragment, "Top 20 Dishes");
        adapter.addFrag(mUserReviewFragment, "Review");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
    }

    public void addListener(IUserFragmentListener iUserFragmentListener) {
        miUserFragmentListener = iUserFragmentListener;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_nav_icon:
                if(miUserFragmentListener != null){
                    miUserFragmentListener.showNavigationDrawer();
                }
                break;
        }
    }
}
