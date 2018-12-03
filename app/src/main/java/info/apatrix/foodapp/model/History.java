package info.apatrix.foodapp.model;

public class History {
    private String datetime;
    private String order_id;
    private String total;
    private String gst;
    private String value;
    private String order_status;

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public History(String datetime, String order_id, String total, String gst,String value,String order_status) {
        this.datetime = datetime;
        this.order_id = order_id;
        this.total = total;
        this.gst = gst;
        this.value = value;
        this.order_status=order_status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
}
