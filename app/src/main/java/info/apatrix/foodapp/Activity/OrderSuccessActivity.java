package info.apatrix.foodapp.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.apatrix.foodapp.R;

public class OrderSuccessActivity extends AppCompatActivity {
    @BindView(R.id.id_order)
    TextView tv_order;
    @BindView(R.id.id_delivered_soon)
    TextView tv_delivered_soon;
    @BindView(R.id.id_continue)
    Button bt_continue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        Typeface bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Bold.ttf");
        Typeface regular = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Regular.ttf");
        Typeface semi_bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-SemiBold.ttf");

        ButterKnife.bind(this);
        tv_order.setTypeface(bold);
        tv_delivered_soon.setTypeface(regular);
        bt_continue.setTypeface(semi_bold);
    }

    @OnClick(R.id.id_continue)
    public void onButtonClick(View view) {
        Intent i=new Intent(getApplicationContext(),HomeMenuActivity.class);
        startActivity(i);
        finish();
    }
}
