package info.apatrix.foodapp.fragments;



import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.apatrix.foodapp.R;
import info.apatrix.foodapp.adapter.ViewPagerAdapter;
import info.apatrix.foodapp.api.APIService;
import info.apatrix.foodapp.api.APIUrl;
import info.apatrix.foodapp.api.ApiModule;
import info.apatrix.foodapp.model.ResultSubCategory;
import info.apatrix.foodapp.model.SubCategoryList;
import info.apatrix.foodapp.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeverageFragment extends Fragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    String parent_d = "";
    private static final int WIDTH_INDEX = 0;
    private static final int DIVIDER_FACTOR = 3;
    private ArrayList<SubCategoryList> item;

    ViewPagerAdapter adapter;

    public BeverageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int[] wh = Utils.getScreenSize(getContext());
        int tabMinWidth = wh[WIDTH_INDEX] / DIVIDER_FACTOR;

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_beverage, container, false);
        ButterKnife.bind(this, root);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#cc202c"), Color.parseColor("#ffffff"));
        tabLayout.setMinimumWidth(tabMinWidth);
        if (getArguments() != null) {
            parent_d = getArguments().getString("parent_id");
        }
        getSubCategory(parent_d);
        return root;
    }


    private void getSubCategory(String parent_id) {
        APIService service = ApiModule.getAPIService();
        Call<ResultSubCategory> call = service.getSubCategoryList(parent_id);
        call.enqueue(new Callback<ResultSubCategory>() {
            @Override
            public void onResponse(Call<ResultSubCategory> call, Response<ResultSubCategory> response) {
                try {
                    if (response.body().getMessage().equals("success")) {
                        item = response.body().getItem();


                    } else {
                        Toast.makeText(getContext(), "No data in this category", Toast.LENGTH_SHORT).show();
                    }

                    Log.e("item", "my item size " + item.size());

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Exception ", e.getMessage());


                }

            }

            @Override
            public void onFailure(Call<ResultSubCategory> call, Throwable t) {
                Log.e("MyTag", "requestFailed", t);
                Log.e("Failure ", t.getMessage());

            }
        });
    }
}