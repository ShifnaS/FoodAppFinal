package info.apatrix.foodapp.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.apatrix.foodapp.R;
import info.apatrix.foodapp.adapter.SimplePagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements  TabLayout.OnTabSelectedListener {

    //This is our tablayout
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    //This is our viewPager
    @BindView(R.id.pager)
    ViewPager viewPager;

    public HistoryFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this,root);

        //Initializing the tablayout

        //Adding the tabs using addTab() method

        tabLayout.addTab(tabLayout.newTab().setText("PAST ORDERS"));
        tabLayout.addTab(tabLayout.newTab().setText("PENDING"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager

        //Creating our pager adapter
        SimplePagerAdapter adapter = new SimplePagerAdapter(getFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        //Toast.makeText(getContext(), ""+tabLayout.getTabAt(position), Toast.LENGTH_SHORT).show();
                        tabLayout.getTabAt(position).select();
                    }
                });

        return  root;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}

