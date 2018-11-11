package info.apatrix.foodapp.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.apatrix.foodapp.Activity.HomeMenuActivity;
import info.apatrix.foodapp.Activity.OrderSuccessActivity;
import info.apatrix.foodapp.Activity.ResetSuccessActivity;
import info.apatrix.foodapp.Helper.SQLiteOperations;
import info.apatrix.foodapp.R;
import info.apatrix.foodapp.adapter.CartAdapter;
import info.apatrix.foodapp.api.APIService;
import info.apatrix.foodapp.api.ApiModule;
import info.apatrix.foodapp.model.Customer;
import info.apatrix.foodapp.model.Order;
import info.apatrix.foodapp.model.Products;
import info.apatrix.foodapp.model.ResultCustomerData;
import info.apatrix.foodapp.utils.SharedPreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment  {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    @BindView(R.id.item_total_text)
    TextView item_total_text;
    @BindView(R.id.to_pay_text)
    TextView to_pay_text;
    @BindView(R.id.gst_text)
    TextView gst_text;

    @BindView(R.id.item_total)
    TextView item_total;
    @BindView(R.id.to_pay)
    TextView to_pay;
    @BindView(R.id.gst)
    TextView item_gst;
    @BindView(R.id.order_details)
    TextView item_order_details;
    @BindView(R.id.restaurant_bill)
    TextView item_restaurant_bill;
    @BindView(R.id.your_cart)
    TextView item_your_cart;




    @BindView(R.id.empty)
    LinearLayout empty;
    @BindView(R.id.not_empty)
    LinearLayout not_empty;
    @BindView(R.id.confirm_order)
    Button confirm_order;

    Double totl=0.0;
    private List<Products> productsList;
    private CartAdapter mAdapter;
    CartListner cartListner;
    Products p;
    SQLiteOperations sqLiteOperations;
    JsonArray ja;
    private IntentIntegrator qrScan;


    HomeMenuActivity.BadgeUpdate badgeUpdate;
    private ViewGroup mainLayout;


    private static final int MY_PERMISSION_REQUEST_CAMERA = 0;

  //  private PointsOverlayView pointsOverlayView;
  BottomNavigationView navigation;
    public CartFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CartFragment(HomeMenuActivity.BadgeUpdate badgeUpdate) {
        this.badgeUpdate=badgeUpdate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this,root);
        LinearLayout ll=root.findViewById(R.id.cartt);

        navigation=root.findViewById(R.id.navigation);


        Typeface bold = Typeface.createFromAsset(getContext().getAssets(),  "fonts/Poppins-Bold.ttf");
        Typeface regular = Typeface.createFromAsset(getContext().getAssets(),  "fonts/Poppins-Regular.ttf");
        Typeface semi_bold = Typeface.createFromAsset(getContext().getAssets(),  "fonts/Poppins-SemiBold.ttf");
        Typeface medium = Typeface.createFromAsset(getContext().getAssets(),  "fonts/Poppins-Medium.ttf");
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.setHorizontalScrollBarEnabled(false);
        root.setVerticalScrollBarEnabled(false);
        root.setHorizontalScrollBarEnabled(false);

        item_your_cart.setTypeface(bold);
        item_order_details.setTypeface(regular);
        item_restaurant_bill.setTypeface(regular);
        confirm_order.setTypeface(semi_bold);

        item_total_text.setTypeface(medium);
        gst_text.setTypeface(medium);
        to_pay_text.setTypeface(medium);

        item_total.setTypeface(medium);
        item_gst.setTypeface(medium);
        to_pay.setTypeface(medium);

        qrScan  = new IntentIntegrator(this.getActivity()).forSupportFragment(this);
        mainLayout=root.findViewById(R.id.cartt);
        ja=new JsonArray();


        sqLiteOperations=new SQLiteOperations(getContext());
        cartListner=new CartListner() {
            @Override
            public void cartList(Double total) {
                double gst=total*(0.18);
                double sum=gst+total;
                item_total.setText("₹" +total);
                item_gst.setText("₹" +String.format("%.2f", gst));
                to_pay.setText("₹" +String.format("%.2f", sum));
            }

            @Override
            public void order(int product_id, int quantity) {

               // Toast.makeText(getContext(), " pid "+product_id+" quan "+quantity, Toast.LENGTH_SHORT).show();
               JsonObject jo=new JsonObject();
             //  Toast.makeText(getContext(), ""+quantity, Toast.LENGTH_SHORT).show();
               jo.addProperty("product_id",product_id);
               jo.addProperty("item_quantity",quantity);
               ja.add(jo);

            }

            @Override
            public void onItemClick(View v, int position,int id) {
                final Products item = productsList.get(position);

              //  Toast.makeText(getContext(), ""+item.getProduct_id(), Toast.LENGTH_SHORT).show();
                SQLiteOperations sqLiteOperations=new SQLiteOperations(getContext());
                int r= sqLiteOperations.deletedata(id);
              //  Toast.makeText(getContext(), "value of r "+r, Toast.LENGTH_SHORT).show();

                if(r!=0)
                {
                    productsList.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyItemRangeChanged(position, productsList.size());
                    if(productsList.isEmpty())
                    {
                        empty.setVisibility(View.VISIBLE);
                        not_empty.setVisibility(View.GONE);

                    }
                    else
                    {
                        not_empty.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.GONE);


                    }
                    badgeUpdate.updateCount();
                }
                else
                {
                    Toast.makeText(getContext(), "failed to delete", Toast.LENGTH_SHORT).show();

                }

            }
        } ;


        productsList = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        SQLiteOperations sqLiteOperations=new SQLiteOperations(getContext());
        productsList= sqLiteOperations.getProducts();
        if(productsList.isEmpty())
        {
            empty.setVisibility(View.VISIBLE);
            not_empty.setVisibility(View.GONE);

        }
        else
        {
            not_empty.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);


        }
        mAdapter = new CartAdapter(getContext(), productsList,cartListner);
        recyclerView.setAdapter(mAdapter);



        return root;
    }


    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSION_REQUEST_CAMERA) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(mainLayout, "Camera permission was granted.", Snackbar.LENGTH_SHORT).show();
            qrScan.initiateScan();

        } else {
            Snackbar.make(mainLayout, "Camera permission request was denied.", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
            Snackbar.make(mainLayout, "Camera access is required to display the camera preview.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityCompat.requestPermissions(getActivity(), new String[] {
                            Manifest.permission.CAMERA
                    }, MY_PERMISSION_REQUEST_CAMERA);
                }
            }).show();
        } else {
            Snackbar.make(mainLayout, "Permission is not available. Requesting camera permission.",
                    Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                    Manifest.permission.CAMERA
            }, MY_PERMISSION_REQUEST_CAMERA);
        }
    }
    @OnClick(R.id.confirm_order)
    public void onButtonLoginClick(View view) {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            qrScan.setPrompt(getActivity().getString(R.string.scan_bar_code));
            qrScan.setBeepEnabled(true);
            qrScan.setOrientationLocked(false);
            qrScan.setCameraId(0);  // Use a specific camera of the device
            qrScan.initiateScan();


        } else {
            requestCameraPermission();
        }




    }

    private void orderNow(JsonObject jo) {

        APIService service = ApiModule.getAPIService();
        Call<ResultCustomerData> call = service.order(jo);


        call.enqueue(new Callback<ResultCustomerData>() {
            @Override
            public void onResponse(Call<ResultCustomerData> call, Response<ResultCustomerData> response) {
                try
                {
                  //  Toast.makeText(getContext(), "status "+response.body().isStatus(), Toast.LENGTH_SHORT).show();
                    if(response.body().isStatus())
                    {
                        // JSONObject jo =response.body().getResponse();
                        //Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        sqLiteOperations.deleteallData();
                        Intent i=new Intent(getContext(),OrderSuccessActivity.class);
                        startActivity(i);
                        getActivity().finish();


                    }
                    else
                    {
                        Toast.makeText(getContext(), "Failed Please Try Again", Toast.LENGTH_SHORT).show();
                    }

                    //  Log.e("item","my item33333333"+item.size());

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e("Exception ","2222222222222222222 "+e.getMessage());


                }

            }

            @Override
            public void onFailure(Call<ResultCustomerData> call, Throwable t) {
                Log.e("MyTag", "requestFailed", t);
                Log.e("Failure ",t.getMessage());

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    String table_id=result.getContents();
                    //Toast.makeText(getContext(), "table id "+table_id, Toast.LENGTH_SHORT).show();
                    int user_id= SharedPreferenceUtils.getInstance(getContext()).getValue("userid");
                    JsonObject jo=new JsonObject();
                    try
                    {
                       // Toast.makeText(getContext(), " size ja "+ja.toString(), Toast.LENGTH_SHORT).show();
                        jo.addProperty("customer_id",user_id);
                        jo.addProperty("tab_id",table_id);
                        jo.add("cart",ja);

                        orderNow(jo);
                        Log.e("Cart Dtata","//////////////////////////////////// "+jo.toString());


                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public interface CartListner {
        void cartList(Double total);
        void order(int product_id,int quantity);
        void onItemClick(View v, int position,int id);

    }






}
