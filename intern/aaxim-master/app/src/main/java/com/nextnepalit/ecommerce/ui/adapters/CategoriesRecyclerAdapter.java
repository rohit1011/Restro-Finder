package com.nextnepalit.ecommerce.ui.adapters;

import android.content.Context;
import android.graphics.Paint;
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
import com.nextnepalit.ecommerce.data.models.Category;
import com.nextnepalit.ecommerce.data.models.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoriesRecyclerAdapter extends RecyclerView.Adapter<CategoriesRecyclerAdapter.ViewHolder> {

    private List<Category> mCategoryList;
    private Context mContext;
    public CategoriesRecyclerAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public CategoriesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_search_items, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesRecyclerAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(mCategoryList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        if (mCategoryList == null){
            return 0;
        }
        else {
            return mCategoryList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.category_text_view);
        }
    }

    public void setCategoryList(List<Category> categoryList) {
        mCategoryList = categoryList;
        Log.i("SETCategory",mCategoryList.size()+"");
        notifyDataSetChanged();
    }
}
