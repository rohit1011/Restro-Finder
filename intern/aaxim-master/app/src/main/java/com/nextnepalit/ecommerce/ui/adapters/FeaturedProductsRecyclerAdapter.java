package com.nextnepalit.ecommerce.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextnepalit.ecommerce.R;
import com.nextnepalit.ecommerce.data.models.FeaturedProducts;
import com.nextnepalit.ecommerce.data.models.SectionData;
import com.nextnepalit.ecommerce.ui.ItemDescriptionActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeaturedProductsRecyclerAdapter extends RecyclerView.Adapter<FeaturedProductsRecyclerAdapter.ViewHolder> {

    private List<SectionData> mFeaturedProductsList;
    private Context mContext;

    public FeaturedProductsRecyclerAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public FeaturedProductsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.latest_products_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FeaturedProductsRecyclerAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(mFeaturedProductsList.get(viewHolder.getAdapterPosition()).getName());
        Picasso.get().load(Uri.parse(mFeaturedProductsList.get(viewHolder.getAdapterPosition()).getImg())).
                centerInside().
                resize(viewHolder.item_image.getWidth(), 160)
                .into(viewHolder.item_image);
        viewHolder.price.setPaintFlags(viewHolder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.price.setText(mFeaturedProductsList.get(viewHolder.getAdapterPosition()).getProductPrice()+"");

        viewHolder.sale_price.setText(mFeaturedProductsList.get(viewHolder.getAdapterPosition()).getSalePrice()+"");

        float discount_rate = ((mFeaturedProductsList.get(viewHolder.getAdapterPosition()).getSalePrice()-
                mFeaturedProductsList.get(viewHolder.getAdapterPosition()).getProductPrice())/mFeaturedProductsList.get(viewHolder.getAdapterPosition()).getProductPrice());
        viewHolder.discount_rate.setText(discount_rate+"");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent descriptionActivity = new Intent(mContext, ItemDescriptionActivity.class);
                descriptionActivity.putExtra("SLUG", mFeaturedProductsList.get(viewHolder.getAdapterPosition()).getSlug());
                descriptionActivity.putExtra(mContext.getString(R.string.category_id_item), mFeaturedProductsList.get(viewHolder.getAdapterPosition()).getId());
                descriptionActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(descriptionActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mFeaturedProductsList == null) {
            return 0;
        } else {
            return mFeaturedProductsList.size();
        }
    }

    public void setFeaturedProductsList(List<SectionData> featuredProductsList) {
        mFeaturedProductsList = featuredProductsList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, price, sale_price, discount_rate;
        ImageView item_image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_title);
            price = itemView.findViewById(R.id.price);
            item_image = itemView.findViewById(R.id.item_image);
            sale_price = itemView.findViewById(R.id.sale_price);
            discount_rate = itemView.findViewById(R.id.discount_rate);
        }
    }
}
