package info.apatrix.foodapp.api;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pc on 22-Oct-18.
 */

public class ApiModule {
    private static Retrofit getRetrofitInstance()
    {
        OkHttpClient client=new OkHttpClient();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public static APIService getAPIService()
    {
        return getRetrofitInstance().create(APIService.class);
    }

}
