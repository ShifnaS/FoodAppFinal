package info.apatrix.foodapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pc on 02-Aug-18.
 */

public class ResultSubCategory {


    @SerializedName("response")
    private ArrayList<SubCategoryList> item;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;

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

    public ArrayList<SubCategoryList> getItem() {
        return item;
    }

    public void setItem(ArrayList<SubCategoryList> item) {
        this.item = item;
    }
}
