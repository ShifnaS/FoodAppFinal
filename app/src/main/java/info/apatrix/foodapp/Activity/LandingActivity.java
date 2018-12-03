package info.apatrix.foodapp.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.apatrix.foodapp.R;

public class LandingActivity extends AppCompatActivity {
    @BindView(R.id.signup)
    Button bt_signUp;

    @BindView(R.id.login)
    Button bt_login;

    @BindView(R.id.food_app)
    TextView tv_foodApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_landing);

        ButterKnife.bind(this);

        Typeface bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Bold.ttf");
        Typeface semi_bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-SemiBold.ttf");
        tv_foodApp.setTypeface(bold);
        bt_login.setTypeface(semi_bold);
        bt_signUp.setTypeface(semi_bold);
        tv_foodApp.setText("ACTORS MUSICAL");

    }
    @OnClick(R.id.signup)
    public void onButtonClick(View view) {
        Intent i=new Intent(getApplicationContext(),SignupActivity.class);
        startActivity(i);
        finish();
    }
    @OnClick(R.id.login)
    public void onLoginClick(View view) {
        Intent i=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);
        finish();
    }

}
