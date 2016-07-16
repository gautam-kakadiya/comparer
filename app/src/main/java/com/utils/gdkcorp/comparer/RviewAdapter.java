package com.utils.gdkcorp.comparer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by Gautam Kakadiya on 05-06-2016.
 */
public class RviewAdapter extends RecyclerView.Adapter<RviewAdapter.MyViewHolder> {

    public LayoutInflater inflater;
    public List<AmazonProductInfo> data = Collections.emptyList();
    public Context context;

    public RviewAdapter (Context context,List<AmazonProductInfo> data){
        inflater = LayoutInflater.from(context);
        this.context= context;
        this.data = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.amazon_product_item  ,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AmazonProductInfo current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.brand.setText(current.getBrand());
        holder.price.setText(current.getPrice());
        Picasso.with(context).load(current.getImageURL()).into(holder.imgview);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(AmazonProductInfo value) {
        data.add(value);
        notifyDataSetChanged();
    }

    public void clearData() {
        data.clear();
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imgview;
        TextView title,brand,price;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgview = (ImageView) itemView.findViewById(R.id.imgview);
            title= (TextView) itemView.findViewById(R.id.titleview);
            brand = (TextView) itemView.findViewById(R.id.manufacturerview);
            price = (TextView) itemView.findViewById(R.id.priceview);
        }
    }
}
