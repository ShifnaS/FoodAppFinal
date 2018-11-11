package info.apatrix.foodapp.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.apatrix.foodapp.R;
import info.apatrix.foodapp.api.APIService;
import info.apatrix.foodapp.api.ApiModule;
import info.apatrix.foodapp.model.Customer;
import info.apatrix.foodapp.model.Result;
import info.apatrix.foodapp.model.ResultCustomerData;
import info.apatrix.foodapp.utils.NetworkUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.btn_signin)
    ImageButton bt_signIn;

    @BindView(R.id.forget_pas)
    TextView tv_forget_pas;
    @BindView(R.id.enter_reg_email)
    TextView tv_enter_reg_email;

    @BindView(R.id.back_to_login)
    TextView tv_back_to_login;

    @BindView(R.id.input_email)
    EditText et_input_email;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Typeface bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Bold.ttf");
        Typeface regular = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Regular.ttf");
        Typeface semi_bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-SemiBold.ttf");
        Typeface medium = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Medium.ttf");

        tv_forget_pas.setTypeface(bold);
        tv_enter_reg_email.setTypeface(regular);
        tv_back_to_login.setTypeface(medium);
        et_input_email.setTypeface(regular);
    }
    @OnClick(R.id.back_to_login)
    public void onButtonClick(View view) {

        Intent i=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);
        finish();

    }

    private void forgetPassword(String email)
    {
        APIService service = ApiModule.getAPIService();
        Call<Result> call = service.forgetPassword(email);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try
                {
                    if(response.isSuccessful())
                    {
                        if(response.body().getStatus().equalsIgnoreCase("success"))
                        {

                            Toast.makeText(getApplicationContext(),response.body().getMessage() , Toast.LENGTH_SHORT).show();

                            Intent i=new Intent(getApplicationContext(),ResetSuccessActivity.class);
                            startActivity(i);
                            finish();


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),response.body().getMessage() , Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "failed ", Toast.LENGTH_SHORT).show();

                    }



                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e("Exception ",e.getMessage());
                   // Toast.makeText(ForgetPasswordActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();



                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("MyTag", "requestFailed", t);
                Log.e("Failure ",t.getMessage());
                //Toast.makeText(ForgetPasswordActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @OnClick(R.id.btn_signin)
    public void onButtonSignClick(View view) {
        if(isEmailEmpty())
        {
            et_input_email.setError(getString(R.string.email_error));
        }
        else
        {
            String email=et_input_email.getText().toString().trim();

            if(NetworkUtil.isOnline())
            {
                forgetPassword(email);
            }
            else
            {
                Toast.makeText(this, "Please check your network", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean isEmailEmpty() {
        return et_input_email.getText().toString().isEmpty();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
