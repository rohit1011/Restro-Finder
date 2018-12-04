package com.nextnepalit.ecommerce.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nextnepalit.ecommerce.R;
import com.nextnepalit.ecommerce.data.models.LatestProducts;

import java.util.List;

public class LatestProductsRecyclerAdapter extends RecyclerView.Adapter<LatestProductsRecyclerAdapter.ViewHolder> {

    private List<LatestProducts> mLatestProductsList;
    private Context mContext;
    public LatestProductsRecyclerAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public LatestProductsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.latest_products_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LatestProductsRecyclerAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(mLatestProductsList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        if (mLatestProductsList == null){
            return 0;
        }
        else {
            return mLatestProductsList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_title);
        }
    }

    public void setLatestProductsList(List<LatestProducts> latestProductsList) {
        mLatestProductsList = latestProductsList;
        Log.i("SETLATESTPRODUCTS",mLatestProductsList.size()+"");
        notifyDataSetChanged();
    }
}
