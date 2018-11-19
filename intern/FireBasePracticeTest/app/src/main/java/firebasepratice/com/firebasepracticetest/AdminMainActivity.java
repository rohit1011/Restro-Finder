package firebasepratice.com.firebasepracticetest;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import firebasepratice.com.firebasepracticetest.ui.BookBorrowedFragment;
import firebasepratice.com.firebasepracticetest.ui.HomeFragment;
import firebasepratice.com.firebasepracticetest.ui.UsersDetailFragment;

public class AdminMainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FloatingActionButton floatingActionButtonBook,floatingActionButtonEntry;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   // mTextMessage.setText(R.string.title_home);
                    loadFragment(new HomeFragment());
                    return true;
                case R.id.navigation_dashboard:
                  //  mTextMessage.setText(R.string.title_dashboard);
                    loadFragment(new BookBorrowedFragment());
                    return true;
                case R.id.navigation_notifications:
                  //  mTextMessage.setText(R.string.title_notifications);
                    loadFragment(new UsersDetailFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        floatingActionButtonBook = findViewById(R.id.add_book);
        floatingActionButtonEntry = findViewById(R.id.add_entry);

        floatingActionButtonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddBookActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Book Added",Toast.LENGTH_SHORT).show();
            }
        });
        floatingActionButtonEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BookEntryActivity.class);
                startActivity(intent);
            }
        });

        loadFragment(new HomeFragment());
    }
private  void loadFragment(Fragment fragment)
{
    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment).commit();
}
}
