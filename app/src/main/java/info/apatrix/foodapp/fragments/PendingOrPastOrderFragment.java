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
import info.apatrix.foodapp.R;
import info.apatrix.foodapp.adapter.HistoryAdapter;
import info.apatrix.foodapp.adapter.ProductListAdapter;
import info.apatrix.foodapp.api.APIService;
import info.apatrix.foodapp.api.ApiModule;
import info.apatrix.foodapp.model.History;
import info.apatrix.foodapp.model.ResultHistoryList;
import info.apatrix.foodapp.model.ResultProductList;
import info.apatrix.foodapp.utils.SharedPreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingOrPastOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<History> historyList = new ArrayList<>();
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private HistoryAdapter mAdapter;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    int status=0;
    public PendingOrPastOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_pending_or_past_order, container, false);
        ButterKnife.bind(this,root);
        Bundle b=getArguments();
        status=b.getInt("status",0);
      //  Toast.makeText(getContext(), ""+status, Toast.LENGTH_SHORT).show();
       // recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.setHorizontalScrollBarEnabled(false);
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
                prepareHistoryData(status);
            }
        });

        return root;
    }

    private void prepareHistoryData(int status) {
        int userid= SharedPreferenceUtils.getInstance(getContext()).getValue("userid");
        //Toast.makeText(getContext(), "userd "+userid+"status "+status, Toast.LENGTH_SHORT).show();
        APIService service = ApiModule.getAPIService();
        Call<ResultHistoryList> call = service.getHistory(status,userid);
        call.enqueue(new Callback<ResultHistoryList>() {
            @Override
            public void onResponse(Call<ResultHistoryList> call, Response<ResultHistoryList> response) {
                try
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                //    Toast.makeText(getContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if(response.body().getMessage().equals("success"))
                    {
                        historyList=response.body().getItem();
                        mAdapter = new HistoryAdapter(historyList,getContext());
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                    }
                    else
                    {
                        Toast.makeText(getContext(), "No data in this category", Toast.LENGTH_SHORT).show();
                    }

                    Log.e("item","my item33333333"+historyList.size());

                }
                catch (Exception e)
                {
                    mSwipeRefreshLayout.setRefreshing(false);

                    e.printStackTrace();
                    Log.e("Exception ",e.getMessage());


                }

            }

            @Override
            public void onFailure(Call<ResultHistoryList> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);

                Log.e("MyTag", "requestFailed", t);
                Log.e("Failure ",t.getMessage());

            }
        });



    }

    @Override
    public void onRefresh() {
        prepareHistoryData(status);


    }
}
