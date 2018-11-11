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

public class ResetSuccessActivity extends AppCompatActivity {
    @BindView(R.id.id_reset)
    TextView tv_reset;
    @BindView(R.id.id_new_pass)
    TextView tv_new_pass;
    @BindView(R.id.login)
    Button bt_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_success);
        Typeface bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Bold.ttf");
        Typeface regular = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Regular.ttf");
        Typeface semi_bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-SemiBold.ttf");

        ButterKnife.bind(this);
        tv_reset.setTypeface(bold);
        tv_new_pass.setTypeface(regular);
        bt_login.setTypeface(semi_bold);
    }
    @OnClick(R.id.login)
    public void onButtonClick(View view) {
        Intent i=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);
        finish();
    }
}
