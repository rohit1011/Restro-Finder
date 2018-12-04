package com.nextnepalit.ecommerce.ui.adapters;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.nextnepalit.ecommerce.R;
import com.nextnepalit.ecommerce.data.models.CartValues;
import com.nextnepalit.ecommerce.viewmodels.CartValuesViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private List<CartValues> mItemValueList;
    private Fragment mContext;
    private CartValuesViewModel cartViewModel;
    private Float totalPrice = 0F;

    public CartListAdapter(Fragment context) {
        mContext = context;
        cartViewModel = ViewModelProviders.of(mContext).get(CartValuesViewModel.class);
    }

    @NonNull
    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext.getContext()).inflate(R.layout.cart_list_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartListAdapter.ViewHolder viewHolder, int i) {

        float total = mItemValueList.get(i).getPrice() * mItemValueList.get(i).getNumberOfItems();
        viewHolder.cartListTv.setText(mItemValueList.get(viewHolder.getAdapterPosition()).getName());
        viewHolder.itemsInCart.setText(String.format(Locale.US, "Items in cart: %d", mItemValueList.get(i).getNumberOfItems()));
        viewHolder.textView2.setText("Total price: " + total);
        viewHolder.close_btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartViewModel.deleteData(mItemValueList.get(viewHolder.getAdapterPosition()).getId());
            }
        });
        viewHolder.update_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCart(mItemValueList.get(viewHolder.getAdapterPosition()).getId(), mItemValueList.get(viewHolder.getAdapterPosition()).getNumberOfItems());
            }
        });

        Picasso.get().load(mItemValueList.get(i).getImageUrl()).into(viewHolder.imageView3);
    }

    @Override
    public int getItemCount() {
        if (mItemValueList == null) {
            return 0;
        } else {
            return mItemValueList.size();
        }
    }

    public void setItemValueList(List<CartValues> itemValueList) {
        mItemValueList = itemValueList;
    }

    void updateCart(final int id, int selectedPrice) {

        final Dialog alertDialog = new Dialog(mContext.getContext());
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.layout_add_cart_dialog);
        final NumberPicker numberPicker = alertDialog.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(selectedPrice);
        numberPicker.setMinValue(1);
        numberPicker.setEnabled(true);

        Button addToCartButton = alertDialog.findViewById(R.id.add_to_cart_button);
        addToCartButton.setText("Update Cart");
        ImageView imageView2 = alertDialog.findViewById(R.id.imageView2);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartViewModel.updateCartAmount(id, numberPicker.getValue());
                alertDialog.dismiss();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView cartListTv, itemsInCart, textView2;
        ImageView imageView3, close_btn_cart;
        Button update_cart_btn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartListTv = itemView.findViewById(R.id.cartListTv);
            itemsInCart = itemView.findViewById(R.id.itemsInCart);
            imageView3 = itemView.findViewById(R.id.imageView3);
            textView2 = itemView.findViewById(R.id.textView2);
            close_btn_cart = itemView.findViewById(R.id.close_btn_cart);
            update_cart_btn = itemView.findViewById(R.id.update_cart_btn);
        }
    }
}
