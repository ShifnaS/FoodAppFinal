package info.apatrix.foodapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pc on 24-Oct-18.
 */

public class ResultProductList {

    @SerializedName("response")
    private ArrayList<Products> item;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;

    public ArrayList<Products> getItem() {
        return item;
    }

    public void setItem(ArrayList<Products> item) {
        this.item = item;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
