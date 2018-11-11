package info.apatrix.foodapp.model;
import com.google.gson.annotations.SerializedName;

public class ResultCustomerData {

   /* @SerializedName("response")
    private JSONObject response;*/
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("response")
    private Customer response;

    public Customer getResponse() {
        return response;
    }

    public void setResponse(Customer response) {
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