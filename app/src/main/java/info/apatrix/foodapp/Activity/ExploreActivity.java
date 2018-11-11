package info.apatrix.foodapp.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.apatrix.foodapp.R;

public class ExploreActivity extends AppCompatActivity {
    @BindView(R.id.id_allSet)
    TextView tv_allSet;
    @BindView(R.id.id_thanks)
    TextView tv_thanks;
    @BindView(R.id.login)
    Button bt_explore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        Typeface bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Bold.ttf");
        Typeface regular = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Regular.ttf");
        Typeface semi_bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-SemiBold.ttf");

        ButterKnife.bind(this);
        tv_allSet.setTypeface(bold);
        tv_thanks.setTypeface(regular);
        bt_explore.setTypeface(semi_bold);


    }
    @OnClick(R.id.login)
    public void onButtonClick(View view) {

        Intent i=new Intent(getApplicationContext(),HomeMenuActivity.class);
        startActivity(i);
        finish();
    }
}
