package info.apatrix.foodapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hariofspades.incdeclibrary.IncDecImageButton;

import java.util.List;

import info.apatrix.foodapp.Helper.SQLiteOperations;
import info.apatrix.foodapp.R;
import info.apatrix.foodapp.fragments.FoodFragment;
import info.apatrix.foodapp.fragments.ProductsFragment;
import info.apatrix.foodapp.model.Products;

/**
 * Created by pc on 23-Oct-18.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    private Context context;
    private List<Products> productList;
    SQLiteOperations sqLiteOperations;
    ProductsFragment.ProductListner productListner;


    public ProductListAdapter(Context context, List<Products> productList, ProductsFragment.ProductListner productListner) {
        this.context = context;
        this.productList = productList;
        sqLiteOperations=new SQLiteOperations(context);
        this.productListner=productListner;
    }
    @Override
    public ProductListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductListAdapter.MyViewHolder holder, int position) {
        String base_url="http://actors-music.akstech.com.sg/";
        final Products item = productList.get(position);
        holder.name.setText(item.getProname());
        holder.description.setText(item.getProdescrip());
        holder.price.setText("₹" + item.getCost());
        final int pid=item.getProduct_id();
        final String name=holder.name.getText().toString().trim();
        final double price=item.getCost();
        Glide.with(context)
                .load(base_url+item.getPropic())
                .into(holder.thumbnail);
        holder.bt_inc.setConfiguration(LinearLayout.HORIZONTAL,IncDecImageButton.TYPE_INTEGER,
                IncDecImageButton.DECREMENT,IncDecImageButton.INCREMENT);
        holder.bt_inc.setupValues(1,20,1,1);
        holder.bt_inc.enableLongPress(false,false,500);


        if(sqLiteOperations.CheckIsDataAlreadyInDBorNot(pid))
        {
            int quantity=sqLiteOperations.getQuantity(pid);
            holder.bt_add.setVisibility(View.GONE);
            holder.bt_inc.setVisibility(View.VISIBLE);
            holder.bt_inc.setIntNumber(quantity);
        }
        else
        {
            holder.bt_add.setVisibility(View.VISIBLE);
            holder.bt_inc.setVisibility(View.GONE);
        }

        holder.bt_inc.setOnClickListener(new IncDecImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String q=holder.bt_inc.getValue();
                int qq=Integer.parseInt(q);
                productListner.onItemIncClick(view, holder.getPosition(),pid,qq);

            }
        });
        holder.bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productListner.onItemClick(v, holder.getPosition(),pid,name,price);
               /* sqLiteOperations.addtoCart(pid,name,price);
                Toast.makeText(context, "Successfully Added to the Cart", Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, price;
        public ImageView thumbnail;
        public Button bt_add;
        IncDecImageButton bt_inc;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
            price = view.findViewById(R.id.price);
            thumbnail = view.findViewById(R.id.thumbnail);
            bt_add=view.findViewById(R.id.add);
            bt_inc=view.findViewById(R.id.incdec);

            Typeface medium = Typeface.createFromAsset(context.getAssets(),  "fonts/Poppins-Medium.ttf");
            name.setTypeface(medium);
            description.setTypeface(medium);
            price.setTypeface(medium);
            bt_add.setTypeface(medium);

        }
    }
}
