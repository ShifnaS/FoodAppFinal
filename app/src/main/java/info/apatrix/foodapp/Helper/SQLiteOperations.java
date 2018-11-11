package info.apatrix.foodapp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import info.apatrix.foodapp.model.Products;

public class SQLiteOperations {
    static final String NAME="name";
    static final String ID="pid";
    static final String PRICE="price";
    static final String QUANTITY="quantity";

    private DBHelper dbHelper;
    Context context;
    Cursor cursor=null;

    public SQLiteOperations(Context context) {
        dbHelper = new DBHelper(context);
        this.context=context;
    }

    public long addtoCart(int pid,String name,double price)
    {
        Log.e("pid//// ","///// =======  "+pid);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pid", pid);
        values.put("name", name);
        values.put("price",  price);
        values.put("quantity", 1);


        long r=db.insert("tbl_order", null, values);
        db.close(); // Closing database connection
        return r;
    }




    public int deletedata(int pid)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int r= db.delete("tbl_order", "pid = ?", new String[]{String.valueOf(pid)});
        db.close();
        return  r;
    }

    public void deleteallData()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("tbl_order",null, null);
        db.close();
    }
   public int updateCart(int pid,int quantity)
    {
        Log.e("Quantity","88888888888888888888888888888888888888n "+quantity);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
       // q=q+1;
        values.put("quantity",quantity);
        int r=db.update("tbl_order", values,"pid = ?", new String[]{String.valueOf(pid)});
        db.close();
        return  r;

    }

    public  int getTotalQuantity() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int quantity=0;
        int total=0;
        String q="select * from tbl_order";
        cursor=db.rawQuery(q,null);
        if (cursor.moveToFirst()) {
            try {
                do {
                    quantity=cursor.getInt(cursor.getColumnIndex(QUANTITY));
                    total=total+quantity;
                }
                while (cursor.moveToNext());

            }
            catch (Exception e)
            {
                Log.e("EXCEPTION ","eeeeeeeeeeeeeeeeeeeee "+e);

            }
        }

        cursor.close();
        return total;
    }


    public  int getQuantity(int pid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int quantity=0;
        String q="select * from tbl_order where pid = " + pid;
        cursor=db.rawQuery(q,null);
        if (cursor.moveToFirst()) {
            try {
                do {
                    quantity=cursor.getInt(cursor.getColumnIndex(QUANTITY));

                }
                while (cursor.moveToNext());

            }
            catch (Exception e)
            {
                Log.e("EXCEPTION ","eeeeeeeeeeeeeeeeeeeee "+e);

            }
        }

        cursor.close();
        return quantity;
    }

    public  boolean CheckIsDataAlreadyInDBorNot(int pid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String Query = "Select * from tbl_order where pid = " + pid;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public ArrayList<Products> getProducts() {

        Products products;
        ArrayList<Products> productList  = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String q="select * from tbl_order";
        cursor=db.rawQuery(q,null);
        if (cursor.moveToFirst()) {
            try
            {
                do {
                    products=new Products();
                    int id=cursor.getInt(cursor.getColumnIndex(ID));
                    String name=cursor.getString(cursor.getColumnIndex(NAME));
                    double price=cursor.getDouble(cursor.getColumnIndex(PRICE));
                    int quantity=cursor.getInt(cursor.getColumnIndex(QUANTITY));

                    Log.e("title","********* "+name);
                    products.setProname(name);
                    products.setProduct_id(id);
                    products.setCost(price);
                    products.setItem_quantity(quantity);
                    productList.add(products) ;
                } while (cursor.moveToNext());
            }
            catch (Exception e)
            {
                Log.e("EXCEPTION ","eeeeeeeeeeeeeeeeeeeee "+e);
            }
        }
        cursor.close();
        db.close();
        return productList;
    }
}
