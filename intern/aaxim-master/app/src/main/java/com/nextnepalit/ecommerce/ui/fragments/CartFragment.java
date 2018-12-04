package com.nextnepalit.ecommerce.ui.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nextnepalit.ecommerce.R;
import com.nextnepalit.ecommerce.data.models.CartValues;
import com.nextnepalit.ecommerce.data.models.ProductDetails;
import com.nextnepalit.ecommerce.ui.adapters.CartListAdapter;
import com.nextnepalit.ecommerce.viewmodels.CartValuesViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    CartListAdapter cartListAdapter;
    private CartValuesViewModel mViewModel;
    private Float totalamount;
    private Button proceed_button;
    TextView total_price_tv, final_price_text, shipping_text, discount_price;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        cartListAdapter = new CartListAdapter(this);
        RecyclerView cartItemsRV = view.findViewById(R.id.cartItemsRV);
        proceed_button = view.findViewById(R.id.proceed_button);
        total_price_tv = view.findViewById(R.id.total_price_tv);
        final_price_text = view.findViewById(R.id.final_price_text);
        shipping_text = view.findViewById(R.id.shipping_text);
        discount_price = view.findViewById(R.id.discount_price);

        proceed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cartItemsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        cartItemsRV.setAdapter(cartListAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<ProductDetails> productDetailsList = new ArrayList<>();

        mViewModel = ViewModelProviders.of(this).get(CartValuesViewModel.class);
        mViewModel.getCartValueList().observe(this, new Observer<List<CartValues>>() {

            @Override
            public void onChanged(@Nullable final List<CartValues> cartValues) {
                cartListAdapter.setItemValueList(cartValues);
                cartListAdapter.notifyDataSetChanged();
                totalamount = 0F;
                assert cartValues != null;
                for (int i = 0; i < cartValues.size(); i++) {
                    totalamount = totalamount + cartValues.get(i).getNumberOfItems() * cartValues.get(i).getPrice();
                }
                Float shippingFee = 100F;
                Float discountFee = 0F;
                if (totalamount>150000){
                    shippingFee = 0F;
                    discountFee = totalamount/1000;
                }
                total_price_tv.setText(String.format("%s", totalamount));
                shipping_text.setText(String.format("%s", shippingFee));
                discount_price.setText(String.format("%s", discountFee));
                final_price_text.setText(String.format("%s", totalamount+shippingFee-discountFee));
            }
        });
    }

}
