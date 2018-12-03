package info.apatrix.foodapp.Activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.apatrix.foodapp.Helper.BottomNavigationViewHelper;
import info.apatrix.foodapp.Helper.DBHelper;
import info.apatrix.foodapp.Helper.SQLiteOperations;
import info.apatrix.foodapp.R;
import info.apatrix.foodapp.event.Events;
import info.apatrix.foodapp.fragments.CartFragment;
import info.apatrix.foodapp.fragments.FoodFragment;
import info.apatrix.foodapp.fragments.HistoryFragment;
import info.apatrix.foodapp.utils.NetworkUtil;
import info.apatrix.foodapp.utils.SharedPreferenceUtils;

public class HomeMenuActivity extends AppCompatActivity {
    static final int NUM_ITEMS = 10;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    boolean doubleBackToExitPressedOnce = false;
    TextView textCartItemCount;
    int mCartItemCount = 10;
    private View notificationBadge;
    SQLiteOperations sqLiteOperations;
    BadgeUpdate badgeUpdate;

    BottomNavigationItemView itemView;
    BottomNavigationMenuView menuView;

    @BindView(R.id.container)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);
        DBHelper obj=new DBHelper(getApplicationContext());
        ButterKnife.bind(this);
        sqLiteOperations=new SQLiteOperations(getApplicationContext());
        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");
        Typeface regular = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
        Typeface semi_bold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        toolbar_title.setTypeface(semi_bold);
        toolbar_title.setText("ACTORS MUSICAL");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        itemView = (BottomNavigationItemView) menuView.getChildAt(2);
        notificationBadge = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_action_item_layout, menuView, false);


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        Fragment fragment = new FoodFragment();
        Bundle bundle = new Bundle();
        bundle.putString("parent_id", "1");
        fragment.setArguments(bundle);
        loadFragment(fragment, "Home fragment");
        int q=sqLiteOperations.getTotalQuantity();
        setNotificationBadge();
        badgeUpdate=new BadgeUpdate() {
            @Override
            public void updateCount() {
               // Toast.makeText(HomeMenuActivity.this, "hii "+q, Toast.LENGTH_SHORT).show();
                setNotificationBadge();
            }


        };

     }




    // UI updates must run on MainThread
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(Events e) {
        setNotificationBadge();

    }


    private void setNotificationBadge() {
        int quantity=sqLiteOperations.getTotalQuantity();
        itemView.removeView(notificationBadge);
        if(quantity!=0)
        {
                itemView.addView(notificationBadge);
                textCartItemCount = findViewById(R.id.cart_badge);
                textCartItemCount.setText(""+quantity);

        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Bundle bundle;
            String name = "";
            Fragment fragment;
            if (item.isChecked()) {
                item.setChecked(false);
            }
            switch (item.getItemId()) {
                case R.id.navigation_food:
                    name = "Food Fragment";
                    item.setChecked(true);
                    fragment = new FoodFragment();
                    bundle = new Bundle();
                    bundle.putString("parent_id", "1");
                    fragment.setArguments(bundle);

                    loadFragment(fragment, name);
                    return true;
                case R.id.navigation_beverage:
                    name = "Beverage Fragment";
                    item.setChecked(true);
                    fragment = new FoodFragment();
                    bundle = new Bundle();
                    bundle.putString("parent_id", "2");
                    fragment.setArguments(bundle);
                    loadFragment(fragment, name);
                    return true;
                case R.id.navigation_cart:
                    name = "Cart Fragment";
                    item.setChecked(true);
                    fragment = new CartFragment(badgeUpdate);
                    loadFragment(fragment, name);
                    return true;
                case R.id.navigation_history:
                    name = "History Fragment";
                    item.setChecked(true);
                    fragment = new HistoryFragment();
                    loadFragment(fragment, name);
                    return true;
            }
            return false;
        }
    };

    /**/
    private void loadFragment(Fragment fragment, String name) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        //  transaction.addToBackStack(name);
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.change_pass:
                intent = new Intent(getApplicationContext(), ResetActivity.class);
                startActivity(intent);
              //  finish();
                return true;
            case R.id.logout:
                SharedPreferenceUtils.getInstance(getApplicationContext()).removeKey("userid");
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {

    //    super.onBackPressed();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to close the app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //SharedPreferenceUtils.getInstance(getApplicationContext()).removeKey("userid");

                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


    public interface BadgeUpdate {
        void updateCount();


    }
    @Override
    protected void onStart() {
        super.onStart();
        // Register this fragment to listen to event.
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }




       BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = NetworkUtil.getConnectivityStatusString(context);
                if(message.equalsIgnoreCase("not"))
                {
                    Toast.makeText(context, "Not connected to Internet please turn on mobile data or wifi", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(),NetworkErrorActivity.class);
                    startActivity(i);
                }
            }

        };


    @Override
    protected void onResume() {
        super.onResume();
     //   checkNetwork();

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, filter);

    }
    @Override
    protected void onPause() {
        super.onPause();

          unregisterReceiver(broadcastReceiver);

    }
}
