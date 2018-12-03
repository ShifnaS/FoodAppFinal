package info.apatrix.foodapp.model;
import com.google.gson.annotations.SerializedName;

public class ResultCustomer {

   /* @SerializedName("response")
    private JSONObject response;*/
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
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