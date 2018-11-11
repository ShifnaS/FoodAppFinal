package info.apatrix.foodapp.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.apatrix.foodapp.R;
import info.apatrix.foodapp.utils.NetworkUtil;

public class NetworkErrorActivity extends AppCompatActivity {
    @BindView(R.id.refresh)
    Button refresh;
    @BindView(R.id.error)
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_error);
        ButterKnife.bind(this);
        Typeface medium = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Medium.ttf");
        error.setTypeface(medium);

    }
    @OnClick(R.id.refresh)
    public void onButtonLoginClick(View view) {
        //Toast.makeText(this, ""+NetworkUtil.isOnline(), Toast.LENGTH_SHORT).show();
        if(NetworkUtil.isOnline())
        {
            Intent i=new Intent(getApplication(),HomeMenuActivity.class);
            startActivity(i);
            finish();
        }
    }
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = NetworkUtil.getConnectivityStatusString(context);
            if(message.equalsIgnoreCase("not"))
            {
                Toast.makeText(context, "Not connected to Internet please turn on mobile data or wifi", Toast.LENGTH_SHORT).show();

            }
            else
            {
                Intent i=new Intent(getApplicationContext(),HomeMenuActivity.class);
                startActivity(i);
                finish();

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
