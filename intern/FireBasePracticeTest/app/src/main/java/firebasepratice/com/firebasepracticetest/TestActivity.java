package firebasepratice.com.firebasepracticetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        int numRow = 8;
        int numCol = 15;

        TableLayout tblLayout = (TableLayout) findViewById(R.id.tblLayout);

        for(int i = 0; i < numRow; i++) {
            HorizontalScrollView HSV = new HorizontalScrollView(this);
            HSV.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT));

            TableRow tblRow = new TableRow(this);
            tblRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            tblRow.setBackgroundResource(R.drawable.bookshelf);

            for(int j = 0; j < numCol; j++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.book);

                tblRow.addView(imageView,j);
            }

            HSV.addView(tblRow);
            tblLayout.addView(HSV, i);
        }
    }
}
