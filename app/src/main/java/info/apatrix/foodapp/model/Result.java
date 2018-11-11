package info.apatrix.foodapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 28-Oct-18.
 */

public class Result {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
