package info.apatrix.foodapp.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.apatrix.foodapp.R;
import info.apatrix.foodapp.utils.SharedPreferenceUtils;

public class SplashhScreenActivity extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    @BindView(R.id.food_app)
    TextView tv_foodApp;
    @BindView(R.id.actor)
    TextView tv_actor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashh_screen);
        ButterKnife.bind(this);
        Typeface bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Bold.ttf");
        Typeface medium = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Medium.ttf");
        tv_actor.setTypeface(medium);
        tv_foodApp.setTypeface(bold);
        tv_foodApp.setText("ACTORS MUSICAL");


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                int id=SharedPreferenceUtils.getInstance(getApplicationContext()).getValue("userid");
                if(id!=0)
                {
                    Intent i = new Intent(SplashhScreenActivity.this, HomeMenuActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(SplashhScreenActivity.this, LandingActivity.class);
                    startActivity(i);
                    finish();
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
