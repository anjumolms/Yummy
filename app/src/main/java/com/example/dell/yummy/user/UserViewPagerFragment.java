package com.example.dell.yummy.user;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.yummy.IFragmentListener;
import com.example.dell.yummy.R;
import com.example.dell.yummy.user.dishes.UserDishesFragment;
import com.example.dell.yummy.user.reviews.UserReviewFragment;
import com.example.dell.yummy.user.store.UserStoresFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserViewPagerFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private UserStoresFragment mUserStoresFragment;
    private UserDishesFragment mUserDishesFragment;
    private IFragmentListener miFragmentListener;



    public UserViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_user_view_pager, container, false);

        mUserStoresFragment = new UserStoresFragment();
        mUserStoresFragment.addListener(miFragmentListener);

        mUserDishesFragment = new UserDishesFragment();
        mUserDishesFragment.addListener(miFragmentListener);



        viewPager =  view.findViewById(R.id.viewpager);
        addTabs(viewPager);

        tabLayout =  view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        return view;

    }
    private void addTabs(ViewPager viewPager) {
        FragmentUserPagerAdapter adapter = new FragmentUserPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(mUserStoresFragment , "Stores");
        adapter.addFrag(mUserDishesFragment, "Dishes");
        adapter.addFrag(new UserReviewFragment(), "Review");
        viewPager.setAdapter(adapter);
    }

    public void addListener(IFragmentListener iFragmentListener) {
        miFragmentListener = iFragmentListener;

    }
}
