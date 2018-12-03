package info.apatrix.foodapp.api;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import info.apatrix.foodapp.model.Order;
import info.apatrix.foodapp.model.Result;
import info.apatrix.foodapp.model.ResultCustomer;
import info.apatrix.foodapp.model.ResultHistoryList;
import info.apatrix.foodapp.model.ResultProductList;
import info.apatrix.foodapp.model.ResultCustomerData;
import info.apatrix.foodapp.model.ResultSubCategory;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @FormUrlEncoded
    @POST("customers/register")
    Call<ResultCustomerData> register(
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("name") String name,
            @Field("dob") String dob
    );
    @FormUrlEncoded
    @POST("customers/login")
    Call<ResultCustomerData> login(
            @Field("email") String email,
            @Field("password") String password
    );
    @GET("categories/list_by_id/{input}")
    Call<ResultSubCategory> getSubCategoryList(
           @Path("input") String input
    );
    @GET("product/list_by_id/{input}")
    Call<ResultProductList> getProductList(
            @Path("input") int input
    );
    @FormUrlEncoded
    @POST("customers/resetpassword")
    Call<ResultCustomerData> changePassword(
            @Field("current_password") String current_password,
            @Field("cust_id")int cust_id
    );
    @FormUrlEncoded
    @POST("customers/email")
    Call<Result> forgetPassword(
            @Field("email") String email
    );

    @Headers({
            "Content-Type: application/json;charset=UTF-8"
    })
    @POST("order/order_insert")
    Call<ResultCustomer> order(@Body JsonObject jsonObject);

    @FormUrlEncoded
    @POST("order/order")
    Call<ResultHistoryList> getHistory(
            @Field("status") int status,
            @Field("customer_id") int userid
    );
}
