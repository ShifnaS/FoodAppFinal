package info.apatrix.foodapp.fragments;


import android.annotation.SuppressLint;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.apatrix.foodapp.Activity.HomeMenuActivity;
import info.apatrix.foodapp.R;
import info.apatrix.foodapp.adapter.ViewPagerAdapter;
import info.apatrix.foodapp.api.APIService;
import info.apatrix.foodapp.api.APIUrl;
import info.apatrix.foodapp.api.ApiModule;
import info.apatrix.foodapp.event.Events;
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
public  class FoodFragment extends Fragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

     @BindView(R.id.pager)
     ViewPager viewPager;

     String parent_d="";
     private static final int WIDTH_INDEX = 0;
     private static final int DIVIDER_FACTOR = 3;
     private ArrayList<SubCategoryList> item;
     UpdateBadge updateBadge;

    ViewPagerAdapter adapter;
    public FoodFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int[] wh = Utils.getScreenSize(getContext());
        int tabMinWidth = wh[WIDTH_INDEX] / DIVIDER_FACTOR;

        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_food, container, false);
      // EventBus.getDefault().register(this);

        ButterKnife.bind(this,root);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#4b66ea"), Color.parseColor("#ffffff"));
        tabLayout.setMinimumWidth(tabMinWidth);

        if (getArguments() != null) {
            parent_d= getArguments().getString("parent_id");
        }
        //Toast.makeText(getContext(), "ParentID  "+parent_d, Toast.LENGTH_SHORT).show();
        getSubCategory(parent_d);

        updateBadge=new UpdateBadge() {
            @Override
            public void updateCount() {
               // Toast.makeText(getContext(), "quantity *** "+q, Toast.LENGTH_SHORT).show();
                EventBus.getDefault().postSticky(new Events ());


            }
        };

        return root;
    }




    private void getSubCategory(String parent_id)
    {
        APIService service = ApiModule.getAPIService();
        Call<ResultSubCategory> call = service.getSubCategoryList(parent_id);
        call.enqueue(new Callback<ResultSubCategory>() {
            @Override
            public void onResponse(Call<ResultSubCategory> call, Response<ResultSubCategory> response) {
                try
                {
                    if(response.body().getMessage().equals("success"))
                    {
                        item=response.body().getItem();
                        adapter = new ViewPagerAdapter(getFragmentManager(),item,updateBadge);
                        viewPager.setAdapter(adapter);

                    }
                    else
                    {
                        Toast.makeText(getContext(), "No data in this category", Toast.LENGTH_SHORT).show();
                    }

                    Log.e("item","my item size "+item.size());

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e("Exception ",e.getMessage());

                }

            }

            @Override
            public void onFailure(Call<ResultSubCategory> call, Throwable t) {
                Log.e("MyTag", "requestFailed", t);
                Log.e("Failure ",t.getMessage());

            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // unregister the registered event.
       // EventBus.getDefault().unregister(this);
    }

    public interface UpdateBadge {
        void updateCount();
    }


}
