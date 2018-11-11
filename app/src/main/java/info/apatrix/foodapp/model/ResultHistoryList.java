package info.apatrix.foodapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pc on 24-Oct-18.
 */

public class ResultHistoryList {

    @SerializedName("response")
    private ArrayList<History> response;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;

    public ArrayList<History> getItem() {
        return response;
    }

    public void setItem(ArrayList<History> response) {
        this.response = response;
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
