package com.example.dell.yummy.admin;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.yummy.R;
import com.example.dell.yummy.model.StoreDetails;
import com.example.dell.yummy.user.FragmentUserPagerAdapter;
import com.example.dell.yummy.user.IUserFragmentListener;
import com.example.dell.yummy.user.dishes.UserDishesFragment;
import com.example.dell.yummy.user.reviews.UserReviewFragment;
import com.example.dell.yummy.user.store.UserStoresFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminViewPagerFragment extends Fragment implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AdminStoresFragment mAdminStoresFragment;
    private AdminDishFragment adminDishFragment;
    private IAdminFragmentListener iAdminFragmentListener;
    private List<StoreDetails> mStoreDetails;
    private TextView mTextView;

    public AdminViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_view_pager
                , container, false);
        initFragments();
        viewPager = view.findViewById(R.id.admin_viewpager);
        mTextView = view.findViewById(R.id.admin_nav_icon);
        addTabs(viewPager);
        tabLayout = view.findViewById(R.id.admin_tabs);
        tabLayout.setupWithViewPager(viewPager);
        mTextView.setOnClickListener(this);
        return view;
    }

    private void initFragments() {

        mAdminStoresFragment = new AdminStoresFragment();
        mAdminStoresFragment.addListener(iAdminFragmentListener);

        adminDishFragment = new AdminDishFragment();
        adminDishFragment.addListener(iAdminFragmentListener);

        //LoadDetails();
    }

    private void addTabs(ViewPager viewPager) {
        FragmentUserPagerAdapter adapter = new FragmentUserPagerAdapter(getActivity()
                .getSupportFragmentManager());
        adapter.addFrag(mAdminStoresFragment, "Stores");
        adapter.addFrag(adminDishFragment, "Dishes");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
    }

    public void addListener(IAdminFragmentListener iAdminFragmentListener) {
        this.iAdminFragmentListener = iAdminFragmentListener;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.admin_nav_icon:
                if (iAdminFragmentListener != null) {
                    iAdminFragmentListener.showNavigationDrawer();
                }
                break;
        }
    }
}
