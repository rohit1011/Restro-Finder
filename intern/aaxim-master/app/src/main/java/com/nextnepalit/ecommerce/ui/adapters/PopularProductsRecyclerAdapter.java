package com.nextnepalit.ecommerce.ui.adapters;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextnepalit.ecommerce.R;
import com.nextnepalit.ecommerce.data.models.PopularProducts;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularProductsRecyclerAdapter extends RecyclerView.Adapter<PopularProductsRecyclerAdapter.ViewHolder> {

    private List<PopularProducts> mPopularProductsList;
    private Context mContext;
    public PopularProductsRecyclerAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public PopularProductsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.latest_products_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProductsRecyclerAdapter.ViewHolder viewHolder, int i) {
        viewHolder.item_title.setText(mPopularProductsList.get(i).getName());
        Picasso.get().load(Uri.parse(mPopularProductsList.get(i).getImgs().get(0))).
                centerInside().
                resize(viewHolder.item_image.getWidth(), 160).
                into(viewHolder.item_image);
    }

    @Override
    public int getItemCount() {
        if (mPopularProductsList == null){
            return 0;
        }
        else {
            return mPopularProductsList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_title;
        ImageView item_image;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title = itemView.findViewById(R.id.item_title);
            item_image = itemView.findViewById(R.id.item_image);
        }
    }

    public void setPopularProductsList(List<PopularProducts> popularProductsList) {
        mPopularProductsList = popularProductsList;
        Log.i("SETPopularProducts",mPopularProductsList.size()+"");
        notifyDataSetChanged();
    }
}
