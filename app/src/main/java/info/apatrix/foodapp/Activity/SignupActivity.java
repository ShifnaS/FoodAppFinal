package info.apatrix.foodapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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

public class SignupActivity extends AppCompatActivity {
    @BindView(R.id.btn_signup)
    ImageButton btn_signup;

    @BindView(R.id.enter_details)
    TextView tv_enter_details;

    @BindView(R.id.create_account)
    TextView tv_create_account;

    @BindView(R.id.have_account)
    TextView tv_have_account;

    @BindView(R.id.link_login)
    TextView tv_link_login;

    @BindView(R.id.input_name)
    EditText et_input_name;

    @BindView(R.id.input_dob)
    EditText et_input_dob;

    @BindView(R.id.input_email)
    EditText et_input_email;

    @BindView(R.id.input_phone)
    EditText et_input_phone;

    @BindView(R.id.input_password)
    EditText et_input_password;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ProgressDialog progress;
   /* @BindView(R.id.progressBar)
    ProgressBar progressBar;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
//        progressBar=new ProgressBar(getApplicationContext());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Typeface bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Bold.ttf");
        Typeface regular = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Regular.ttf");
        Typeface semi_bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-SemiBold.ttf");
        Typeface medium = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Medium.ttf");

        tv_enter_details.setTypeface(regular);
        tv_create_account.setTypeface(bold);
        tv_have_account.setTypeface(medium);
        tv_link_login.setTypeface(semi_bold);
        et_input_email.setTypeface(regular);
        et_input_phone.setTypeface(regular);
        et_input_password.setTypeface(regular);
        et_input_name.setTypeface(regular);
        et_input_dob.setTypeface(regular);

        progress = new ProgressDialog(this);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @OnClick(R.id.btn_signup)
    public void onButtonClick(View view) {
        if(isNameEmpty())
        {
            et_input_name.setError(getString(R.string.email_name));

        }
        else if(isDobEmpty())
        {
            et_input_dob.setError(getString(R.string.email_dob));

        }

        else if (isEmailEmpty()){
            et_input_email.setError(getString(R.string.email_error));
        }
        else  if (!isValidEmail()){
            et_input_email.setError(getString(R.string.email_valid_error));
        }
        else if (isPhoneEmpty()){
            et_input_phone.setError(getString(R.string.phone_error));

        }
        else if (isPhoneValid()){
            et_input_phone.setError(getString(R.string.phone_valid_error));

        }
        else if (isPasswordEmpty()){
            et_input_password.setError(getString(R.string.password_error));

        }
        else if(et_input_password.getText().toString().trim().length()<8)
        {
            et_input_password.setError(getString(R.string.password_length_error));

        }
        else
        {
            String email=et_input_email.getText().toString().trim();
            String phone=et_input_phone.getText().toString().trim();
            String password=et_input_password.getText().toString().trim();
            String name=et_input_name.getText().toString().trim();
            String dob=et_input_dob.getText().toString().trim();

            if(NetworkUtil.isOnline())
            {
//                progressBar.setVisibility(View.VISIBLE);
               //progress.
                //progress.setTitle("Loading");
                progress.setMessage("Wait while loading...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();
// To dismiss the dialog

                signUpActivity(email,phone,password,name,dob);
            }
            else
            {
                Toast.makeText(this, "Please check your network", Toast.LENGTH_SHORT).show();
            }
        }



    }
    @OnClick(R.id.link_login)
    public void onButtonLoginClick(View view) {
        Intent i=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void signUpActivity(String email, String phone, String password,String name,String dob)
    {

        APIService service = ApiModule.getAPIService();
        Call<ResultCustomerData> call = service.register(email,phone,password,name,dob);
        call.enqueue(new Callback<ResultCustomerData>() {
            @Override
            public void onResponse(Call<ResultCustomerData> call, Response<ResultCustomerData> response) {
//                progressBar.setVisibility(View.GONE);
                progress.dismiss();


                try
                {

                    if(response.body().getMessage().equalsIgnoreCase("success"))
                    {

                        Customer obj=response.body().getResponse();
                        int userid=Integer.parseInt(obj.getUserid());
                        Toast.makeText(getApplicationContext(),obj.getUserid(), Toast.LENGTH_SHORT).show();
                        SharedPreferenceUtils.getInstance(getApplicationContext()).setValue("userid",userid);
                        Intent i=new Intent(getApplicationContext(),ExploreActivity.class);
                        startActivity(i);
                        finish();

                    }
                    else
                    {
                        Customer obj=response.body().getResponse();
                        Toast.makeText(getApplicationContext(),obj.getValue(), Toast.LENGTH_SHORT).show();
                    }

                    //  Log.e("item","my item33333333"+item.size());

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e("Exception ",e.getMessage());


                }

            }

            @Override
            public void onFailure(Call<ResultCustomerData> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                progress.dismiss();

                Log.e("MyTag", "requestFailed", t);
                Log.e("Failure ",t.getMessage());

            }
        });
    }
    public boolean isNameEmpty() {
        return (et_input_name.getText().toString().isEmpty());
    }
    public boolean isDobEmpty() {
        return (et_input_dob.getText().toString().isEmpty());
    }
    public boolean isPasswordEmpty() {
        return et_input_password.getText().toString().isEmpty();
    }
    public boolean isEmailEmpty() {
        return et_input_email.getText().toString().isEmpty();
    }
    public  boolean isValidEmail()
    {
        String email=et_input_email.getText().toString().trim();
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    public boolean isPhoneEmpty() {
        return (et_input_phone.getText().toString().isEmpty());
    }
    public boolean isPhoneValid() {
        boolean res=false;
        String phone=et_input_phone.getText().toString().trim();
        if(phone.length()!=8)
        {
            res=true;
        }

        return res;
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