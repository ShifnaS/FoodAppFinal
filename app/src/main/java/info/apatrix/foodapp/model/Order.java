package info.apatrix.foodapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pc on 28-Oct-18.
 */

public class Order {
    @SerializedName("customer_id")
    private int customer_id;
    @SerializedName("tab_id")
    private int tab_id;
    @SerializedName("cart")
    private ArrayList<Products> cart;

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getTab_id() {
        return tab_id;
    }

    public void setTab_id(int tab_id) {
        this.tab_id = tab_id;
    }

    public ArrayList<Products> getCart() {
        return cart;
    }

    public void setCart(ArrayList<Products> cart) {
        this.cart = cart;
    }
}
