package info.apatrix.foodapp.model;

/**
 * Created by pc on 21-Oct-18.
 */

public class Products
{
    int product_id;
    String proname;
    String prodescrip;
    double sales;
    String propic;
    int item_quantity;

    public int getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(int item_quantity) {
        this.item_quantity = item_quantity;
    }

    public Products() {
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getProdescrip() {
        return prodescrip;
    }

    public void setProdescrip(String prodescrip) {
        this.prodescrip = prodescrip;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    public String getPropic() {
        return propic;
    }

    public void setPropic(String propic) {
        this.propic = propic;
    }
}
