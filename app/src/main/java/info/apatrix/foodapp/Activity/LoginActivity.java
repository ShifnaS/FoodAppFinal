package info.apatrix.foodapp.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.NavUtils;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.apatrix.foodapp.R;
import info.apatrix.foodapp.api.APIService;
import info.apatrix.foodapp.api.ApiModule;
import info.apatrix.foodapp.model.Customer;
import info.apatrix.foodapp.model.ResultCustomerData;
import info.apatrix.foodapp.utils.NetworkUtil;
import info.apatrix.foodapp.utils.SharedPreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_signin)
    ImageButton bt_signIn;

    @BindView(R.id.sign_continue)
    TextView tv_sign_continue;
    @BindView(R.id.welcome_back)
    TextView tv_welcome_back;
    @BindView(R.id.create_account)
    TextView tv_create_account;
    @BindView(R.id.link_signUp)
    TextView tv_link_signUp;
    @BindView(R.id.input_email)
    EditText et_input_email;
    @BindView(R.id.input_password)
    EditText et_input_password;
    @BindView(R.id.forget_password)
    TextView tv_forget_password;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Typeface bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Bold.ttf");
        Typeface regular = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Regular.ttf");
        Typeface semi_bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-SemiBold.ttf");
        Typeface medium = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Medium.ttf");


        tv_sign_continue.setTypeface(regular);
        tv_welcome_back.setTypeface(bold);
        tv_create_account.setTypeface(medium);
        tv_link_signUp.setTypeface(semi_bold);
        et_input_email.setTypeface(regular);
        et_input_password.setTypeface(regular);
        tv_forget_password.setTypeface(medium);
    }
    @OnClick(R.id.forget_password)
    public void onButtonClick(View view) {
        Intent i=new Intent(getApplicationContext(),ForgetPasswordActivity.class);
        startActivity(i);
        finish();
    }
    @OnClick(R.id.link_signUp)
    public void onSignUpClick(View view) {
        Intent i=new Intent(getApplicationContext(),SignupActivity.class);
        startActivity(i);
        finish();
    }
    @OnClick(R.id.btn_signin)
    public void onButtonLoginClick(View view) {
        if (isEmailEmpty()){
            et_input_email.setError(getString(R.string.email_error));
        }

        else if (isPasswordEmpty()){
            et_input_password.setError(getString(R.string.password_error));

        }

        else
        {
            String email=et_input_email.getText().toString().trim();
            String password=et_input_password.getText().toString().trim();

            if(NetworkUtil.isOnline())
            {
                signInActivity(email,password);

            }
            else
            {
                Toast.makeText(this, "Please check your network", Toast.LENGTH_SHORT).show();
            }

           /* Intent i=new Intent(getApplicationContext(),HomeMenuActivity.class);
            startActivity(i);
            finish();*/
        }




    }

    private void signInActivity(final String email, String password)
    {

        APIService service = ApiModule.getAPIService();
        Call<ResultCustomerData> call = service.login(email,password);
        call.enqueue(new Callback<ResultCustomerData>() {
            @Override
            public void onResponse(Call<ResultCustomerData> call, Response<ResultCustomerData> response) {
                try
                {
                  //  Toast.makeText(LoginActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if(response.body().getMessage().equals("success"))
                    {
                            Customer obj=response.body().getResponse();
                            String customer_id=obj.getUserid();
                            int userid=Integer.parseInt(customer_id);
                            SharedPreferenceUtils.getInstance(getApplicationContext()).setValue("userid",userid);
                            Intent i=new Intent(getApplicationContext(),HomeMenuActivity.class);
                            startActivity(i);
                            finish();


                    }
                    else
                    {
                        Customer obj=response.body().getResponse();

                        Toast.makeText(LoginActivity.this, obj.getValue(), Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e("Exception ",e.getMessage());


                }

            }

            @Override
            public void onFailure(Call<ResultCustomerData> call, Throwable t) {
                Log.e("MyTag", "requestFailed", t);
                Log.e("Failure ",t.getMessage());

            }
        });
    }
    public boolean isPasswordEmpty() {
        return et_input_password.getText().toString().isEmpty();
    }
    public boolean isEmailEmpty() {
        return et_input_email.getText().toString().isEmpty();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
