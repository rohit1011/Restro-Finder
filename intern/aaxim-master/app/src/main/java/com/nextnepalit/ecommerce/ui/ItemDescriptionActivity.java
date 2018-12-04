package com.nextnepalit.ecommerce.ui;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.transition.Explode;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.nextnepalit.ecommerce.R;
import com.nextnepalit.ecommerce.data.models.CartValues;
import com.nextnepalit.ecommerce.data.models.ProductDetails;
import com.nextnepalit.ecommerce.ui.adapters.DescriptionSliderAdapter;
import com.nextnepalit.ecommerce.viewmodels.CartValuesViewModel;
import com.nextnepalit.ecommerce.viewmodels.ProductDescriptionViewModel;

import java.util.List;
import java.util.Locale;

import ss.com.bannerslider.Slider;

public class ItemDescriptionActivity extends AppCompatActivity {

    ProductDescriptionViewModel productDescriptionViewModel;
    Slider viewAnimator;
    TextView productName;
    MaterialCardView specificationCard;
    Button addToCart, dashBtn;
    String itemName, itemImageUrl, itemDescription = "";
    float itemPrice = 0;
    private DescriptionSliderAdapter descriptionSliderAdapter;
    private CartValuesViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);

        cartViewModel = ViewModelProviders.of(this).get(CartValuesViewModel.class);

        viewAnimator = findViewById(R.id.view_animator);
        productName = findViewById(R.id.product_name);
        specificationCard = findViewById(R.id.specification_card);
        dashBtn = findViewById(R.id.dashBtn);
        addToCart = findViewById(R.id.addToCartBtn);
        final TextView collapsible_card_title = findViewById(R.id.collapsible_card_title);
        final TextView specification_textview = findViewById(R.id.specification_textview);
        final TextView features_textview = findViewById(R.id.features_textview);
        final ImageView collapse_image_button = findViewById(R.id.collapse_image_button);
        if (productDescriptionViewModel == null) {
            productDescriptionViewModel = ViewModelProviders.of(this).get(ProductDescriptionViewModel.class);
        }

        final int itemId = getIntent().getIntExtra(getString(R.string.category_id_item), 1);
        final String itemSlug = getIntent().getStringExtra("SLUG");
        cartViewModel.getCartValue(itemId).observe(this, new Observer<List<CartValues>>() {
            @Override
            public void onChanged(@Nullable List<CartValues> cartValues) {
                if (cartValues != null && cartValues.size() > 0) {
                    if (cartValues.get(0).getNumberOfItems() > 0) {
                        addToCart.setText(String.format(Locale.US, "%d Items in cart", cartValues.get(0).getNumberOfItems()));
                    }
                }
            }
        });
        productDescriptionViewModel.getProductDetails(itemSlug).observe(this, new Observer<ProductDetails>() {
            @Override
            public void onChanged(@Nullable ProductDetails productDetails) {

                itemName = productDetails.getData().getName();
                itemPrice = Float.parseFloat(productDetails.getData().getPrice());
                itemDescription = productDetails.getData().getDescription();
                itemImageUrl = productDetails.getData().getImages().get(0).getUrl();
                productName.setText(Html.fromHtml(String.format("<strong><h5>%s</h5></strong>", productDetails.getData().getName().trim())));
                descriptionSliderAdapter = new DescriptionSliderAdapter(productDetails.getData().getImages());
                viewAnimator.setAdapter(descriptionSliderAdapter);
                if (productDetails.getData().getSpecifications().size() > 0) {
                    StringBuilder specificationString = new StringBuilder();
                    for (int i = 0; i < productDetails.getData().getSpecifications().size(); i++) {
                        specificationString.append("<strong><h6>").append(productDetails.getData().getSpecifications().get(i).getTitle()).append("</h6></strong><br>");
                        specificationString.append(productDetails.getData().getSpecifications().get(i).getDescription()).append("<br>");
                    }
                    specification_textview.setText(Html.fromHtml(productDetails.getData().getDescription()));
                }
                if (productDetails.getData().getFeatures().size()>0){
                    StringBuilder featuresString = new StringBuilder();
                    for (int i = 0; i < productDetails.getData().getSpecifications().size(); i++) {
                        featuresString.append("<strong><h6>").append(productDetails.getData().getSpecifications().get(i).getTitle().trim()).append("</h6></strong>");
                        featuresString.append(productDetails.getData().getSpecifications().get(i).getDescription().trim()).append("<br>");
                    }
                    features_textview.setText(Html.fromHtml(featuresString.toString()));
                }
            }
        });

        collapsible_card_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (specification_textview.getVisibility()) {
                    case View.VISIBLE:
                        specification_textview.setVisibility(View.GONE);
                        collapse_image_button.setImageResource(R.drawable.ic_keyboard_arrow_down_24dp);
                        break;

                    case View.GONE:
                        specification_textview.setVisibility(View.VISIBLE);
                        collapse_image_button.setImageResource(R.drawable.ic_keyboard_arrow_up_24dp);
                        break;

                    case View.INVISIBLE:
                        specification_textview.setVisibility(View.VISIBLE);
                        collapse_image_button.setImageResource(R.drawable.ic_keyboard_arrow_up_24dp);
                        break;
                }
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            getWindow().setExitTransition(explode);
        }

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog alertDialog = new Dialog(ItemDescriptionActivity.this);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setContentView(R.layout.layout_add_cart_dialog);
                final NumberPicker numberPicker = alertDialog.findViewById(R.id.numberPicker);
                numberPicker.setMaxValue(itemId);
                numberPicker.setMinValue(1);
                numberPicker.setEnabled(true);

                Button addToCartButton = alertDialog.findViewById(R.id.add_to_cart_button);
                ImageView imageView2 = alertDialog.findViewById(R.id.imageView2);
                addToCartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(findViewById(android.R.id.content), numberPicker.getValue() + " items addded to cart", Snackbar.LENGTH_LONG).show();
                        CartValues cartValues = new CartValues();
                        cartValues.setId(itemId);
                        cartValues.setNumberOfItems(numberPicker.getValue());
                        cartValues.setName(itemName);
                        cartValues.setImageUrl(itemImageUrl);
                        cartValues.setDescription(itemDescription);
                        cartValues.setPrice(itemPrice);
                        cartViewModel.setCartValueList(cartValues);
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
        });

        dashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
