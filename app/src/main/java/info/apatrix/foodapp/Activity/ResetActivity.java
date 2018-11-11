package info.apatrix.foodapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.apatrix.foodapp.R;
import info.apatrix.foodapp.api.APIService;
import info.apatrix.foodapp.api.ApiModule;
import info.apatrix.foodapp.model.ResultCustomerData;
import info.apatrix.foodapp.model.Customer;
import info.apatrix.foodapp.utils.NetworkUtil;
import info.apatrix.foodapp.utils.SharedPreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetActivity extends AppCompatActivity {
    @BindView(R.id.cancel)
    Button bt_cancel;
    @BindView(R.id.ok)
    Button bt_ok;

    @BindView(R.id.reset_pass)
    TextView tv_reset_pass;

    @BindView(R.id.sign_continue)
    TextView tv_sign_continue;

    @BindView(R.id.back_to_login)
    TextView tv_back_to_login;

    @BindView(R.id.input_password)
    EditText et_input_password;

    @BindView(R.id.input_reset_password)
    EditText et_input_Confirm_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        Typeface bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Bold.ttf");
        Typeface regular = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Regular.ttf");
        Typeface semi_bold = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-SemiBold.ttf");
        Typeface medium = Typeface.createFromAsset(getAssets(),  "fonts/Poppins-Medium.ttf");
        ButterKnife.bind(this);

        tv_reset_pass.setTypeface(bold);
        tv_sign_continue.setTypeface(regular);
        tv_back_to_login.setTypeface(semi_bold);
        et_input_Confirm_password.setTypeface(regular);
        et_input_password.setTypeface(regular);
        bt_cancel.setTypeface(semi_bold);
        bt_ok.setTypeface(semi_bold);

    }
    @OnClick(R.id.ok)
    public void onButtonClick(View view) {

        if (isPasswordEmpty()){
            et_input_password.setError(getString(R.string.password_error));

        }
        else if (isConfirmPasswordEmpty()){
            et_input_Confirm_password.setError(getString(R.string.password_error));

        }
        else if(et_input_Confirm_password.getText().toString().trim().length()<8)
        {
            et_input_Confirm_password.setError(getString(R.string.password_length_error));

        }
        else if(et_input_Confirm_password.getText().toString().trim().equalsIgnoreCase(et_input_password.getText().toString().trim()))
        {
            et_input_Confirm_password.setError(getString(R.string.password_same_error));

        }
        else
        {
            String new_pass=et_input_Confirm_password.getText().toString().trim();
            int cust_id= SharedPreferenceUtils.getInstance(getApplicationContext()).getValue("userid");
           // Toast.makeText(this, ""+cust_id, Toast.LENGTH_SHORT).show();
            if(NetworkUtil.isOnline())
            {
                changePassword(new_pass,cust_id);
            }
            else
            {
                Toast.makeText(this, "Please check your network", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void changePassword(String new_pass, int cust_id) {
       // Toast.makeText(this, "new "+new_pass+" id "+cust_id, Toast.LENGTH_SHORT).show();

        APIService service = ApiModule.getAPIService();
        Call<ResultCustomerData> call = service.changePassword(new_pass,cust_id);
        call.enqueue(new Callback<ResultCustomerData>() {
            @Override
            public void onResponse(Call<ResultCustomerData> call, Response<ResultCustomerData> response) {
                try
                {
                    //   Toast.makeText(getApplicationContext(), "success "+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    if(response.body().getMessage().equalsIgnoreCase("success"))
                    {
                        // JSONObject jo =response.body().getResponse();
                        Customer obj=response.body().getResponse();
                        String val=obj.getValue();
                        Toast.makeText(getApplicationContext(), val, Toast.LENGTH_SHORT).show();

                        Intent i=new Intent(getApplicationContext(),ResetSuccessActivity.class);
                        startActivity(i);
                        finish();


                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
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
                Log.e("MyTag", "requestFailed", t);
                Log.e("Failure ",t.getMessage());

            }
        });
    }


    @OnClick(R.id.cancel)
    public void onButtonCancel(View view) {
       // reset();
        Intent i=new Intent(getApplicationContext(),HomeMenuActivity.class);
        startActivity(i);
        finish();

    }
    private void reset() {
        et_input_password.setText(null);
        et_input_Confirm_password.setText(null);
        et_input_password.setError(null);
        et_input_Confirm_password.setError(null);
    }
    public boolean isPasswordEmpty() {
        return et_input_password.getText().toString().isEmpty();
    }
    public boolean isConfirmPasswordEmpty() {
        return et_input_Confirm_password.getText().toString().isEmpty();
    }




   /* @Override
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
    }*/
}
