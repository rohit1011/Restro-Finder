package com.nextnepalit.ecommerce.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.nextnepalit.ecommerce.R;

public class ItemsListActivity extends AppCompatActivity {
    TextView catIdTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        Integer catId = getIntent().getIntExtra("CATID", 0);

        catIdTextView= findViewById(R.id.categoryId);
        catIdTextView.setText(catId+"");

    }
}
