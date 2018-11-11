package info.apatrix.foodapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hariofspades.incdeclibrary.IncDecImageButton;

import java.util.List;

import info.apatrix.foodapp.R;
import info.apatrix.foodapp.fragments.CartFragment;
import info.apatrix.foodapp.model.Products;

/**
 * Created by pc on 27-Oct-18.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private List<Products> productList;
    double total=0;
    CartFragment.CartListner cartListner;
    String q="";
     int quantity=0;


    public CartAdapter(Context context, List<Products> productList, CartFragment.CartListner cartListner) {
        this.context = context;
        this.productList = productList;
        this.cartListner=cartListner;
    }
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item, parent, false);

        return new CartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartAdapter.MyViewHolder holder, int position)

    {
        String base_url="http://actors-music.akstech.com.sg/";
        final Products item = productList.get(position);
        holder.name.setText(item.getProname());
        holder.price.setText("₹" + item.getCost());
        holder.incdec.setConfiguration(LinearLayout.HORIZONTAL,IncDecImageButton.TYPE_INTEGER,
                IncDecImageButton.DECREMENT,IncDecImageButton.INCREMENT);
        holder.incdec.setupValues(1,20,1,1);
        holder.incdec.enableLongPress(false,false,500);

        total=total+item.getCost();
        final int id=item.getProduct_id();

        holder.incdec.setIntNumber(item.getItem_quantity());

        cartListner.cartList(total);
        cartListner.order(id,item.getItem_quantity());


        holder.bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cartListner.onItemClick(v, holder.getPosition(),id);



            }
        });

        holder.incdec.setOnClickListener(new IncDecImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                q=holder.incdec.getValue();
                quantity= Integer.parseInt(q);
               // Toast.makeText(context, ""+quantity, Toast.LENGTH_SHORT).show();
                cartListner.order(id,quantity);

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price;
        public Button bt_delete;
        IncDecImageButton incdec;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            bt_delete=view.findViewById(R.id.delete);
            incdec=(IncDecImageButton)view.findViewById(R.id.incdec);
            Typeface medium = Typeface.createFromAsset(context.getAssets(),  "fonts/Poppins-Medium.ttf");
            name.setTypeface(medium);
            price.setTypeface(medium);
            bt_delete.setTypeface(medium);
           // incdec.setTypeface(medium);

        }
    }


}
