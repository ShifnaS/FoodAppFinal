package info.apatrix.foodapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import info.apatrix.foodapp.R;
import info.apatrix.foodapp.model.History;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<History> historyList;
    private Context context;

    public HistoryAdapter(List<History> historyList,Context context) {
        this.historyList = historyList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        History history = historyList.get(position);
        String total=history.getTotal();
        String gst=history.getGst();
        String status=history.getOrder_status();
        int order_status=Integer.parseInt(status);
        if(order_status==1)
        {
           holder.tv_order_completed.setText("PENDING ORDER");
        }
        else
        {
            holder.tv_order_completed.setText("ORDER COMPLETED");

        }
        int grandTotal=Integer.parseInt(total)+Integer.parseInt(gst);
        String order_date=history.getDatetime();

        SimpleDateFormat spf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date newDate= null;
        try {
            newDate = spf.parse(order_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf=new SimpleDateFormat("dd MMM yyyy, hh:mm:ss aaa");
        order_date = spf.format(newDate);

        System.out.println(order_date);
        holder.tv_date.setText(order_date);
        holder.tv_orderId.setText("Order # "+history.getOrder_id());
        holder.tv_price.setText("$"+grandTotal);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date, tv_orderId, tv_price,tv_order_completed,tv_total;

        public MyViewHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.date);
            tv_orderId = (TextView) view.findViewById(R.id.order_id);
            tv_price = (TextView) view.findViewById(R.id.total);
            tv_order_completed = (TextView) view.findViewById(R.id.order_completed);
            tv_total = (TextView) view.findViewById(R.id.total_text);

            Typeface medium = Typeface.createFromAsset(context.getAssets(),  "fonts/Poppins-Medium.ttf");
            Typeface semi_bold = Typeface.createFromAsset(context.getAssets(),  "fonts/Poppins-SemiBold.ttf");

            tv_date.setTypeface(medium);
            tv_orderId.setTypeface(medium);
            tv_price.setTypeface(medium);
            tv_order_completed.setTypeface(semi_bold);
            tv_total.setTypeface(semi_bold);

        }
    }
}
