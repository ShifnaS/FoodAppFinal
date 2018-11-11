package info.apatrix.foodapp.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.apatrix.foodapp.Helper.SQLiteOperations;
import info.apatrix.foodapp.R;
import info.apatrix.foodapp.adapter.ProductListAdapter;
import info.apatrix.foodapp.api.APIService;
import info.apatrix.foodapp.api.ApiModule;
import info.apatrix.foodapp.model.Products;
import info.apatrix.foodapp.model.ResultProductList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sub_category_id;
    private static final int LOADER_ID = 200;


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private List<Products> productsList;
    private ProductListAdapter mAdapter;
    ProductListner productListner;
    SQLiteOperations sqLiteOperations;
    static FoodFragment.UpdateBadge updateBadgee;

    public ProductsFragment() {
        // Required empty public constructor
    }
    public static ProductsFragment newInstance(int sectionNumber, FoodFragment.UpdateBadge updateBadge) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
       // args.pu
        fragment.setArguments(args);
        updateBadgee=updateBadge;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get store id
        sub_category_id = getArguments() != null ?
                getArguments().getInt(ARG_SECTION_NUMBER) : 1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_products, container, false);


        ButterKnife.bind(this,view);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.setHorizontalScrollBarEnabled(false);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                getProductList();
            }
        });


        sqLiteOperations=new SQLiteOperations(getContext());
        productsList = new ArrayList<>();

        productListner=new ProductListner() {
            @Override
            public void onItemClick(View v, int position, int pid, String name, double price) {
                mAdapter.notifyDataSetChanged();
                long r=sqLiteOperations.addtoCart(pid,name,price);
                if(r!=0)
                {
                    Toast.makeText(getContext(), "Successfully Added to the Cart", Toast.LENGTH_SHORT).show();
                }
                int q=sqLiteOperations.getTotalQuantity();
                updateBadgee.updateCount();

            }

            @Override
            public void onItemIncClick(View v, int position, int pid, int quantity) {
                int r=sqLiteOperations.updateCart(pid,quantity);

                if (r!=0) {
                    mAdapter.notifyDataSetChanged();
                    if(quantity!=1)
                    {
                        Toast.makeText(getContext(), "Successfully Added to the Cart", Toast.LENGTH_SHORT).show();
                    }

                    updateBadgee.updateCount();


                }
            }
        };
        return view;
    }

    private void getProductList() {

        APIService service = ApiModule.getAPIService();
        Call<ResultProductList> call = service.getProductList(sub_category_id);
        call.enqueue(new Callback<ResultProductList>() {
            @Override
            public void onResponse(Call<ResultProductList> call, Response<ResultProductList> response) {
                try
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                  //  Toast.makeText(getContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if(response.body().getMessage().equals("success"))
                    {
                        productsList=response.body().getItem();
                        mAdapter = new ProductListAdapter(getContext(), productsList,productListner);
                        recyclerView.setAdapter(mAdapter);
                    }
                    else
                    {
                        Toast.makeText(getContext(), "No data in this category", Toast.LENGTH_SHORT).show();
                    }

                    Log.e("item","my item33333333"+productsList.size());

                }
                catch (Exception e)
                {
                    mSwipeRefreshLayout.setRefreshing(false);

                    e.printStackTrace();
                    Log.e("Exception ",e.getMessage());


                }

            }

            @Override
            public void onFailure(Call<ResultProductList> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);

                Log.e("MyTag", "requestFailed", t);
                Log.e("Failure ",t.getMessage());

            }
        });
    }

    @Override
    public void onRefresh() {
        getProductList();


    }

    public interface ProductListner {
        public void onItemClick(View v, int position,int pid,String name,double price);
        public void onItemIncClick(View v, int position,int pid,int quantity);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // unregister the registered event.
       // GlobalBus.getBus().unregister(this);
    }


}


