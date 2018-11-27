package firebasepratice.com.firebasepracticetest;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import firebasepratice.com.firebasepracticetest.Objects.BookObject;
import firebasepratice.com.firebasepracticetest.ui.BookBorrowedFragment;
import firebasepratice.com.firebasepracticetest.ui.HomeFragment;
import firebasepratice.com.firebasepracticetest.ui.UsersDetailFragment;

public class AdminMainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FloatingActionButton floatingActionButtonBook,floatingActionButtonEntry,floatingActionButtonReturn;
    private Dialog dialog;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
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
        floatingActionButtonReturn = findViewById(R.id.book_return);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Books");
        floatingActionButtonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddBookActivity.class);
                startActivity(intent);
            }
        });
        floatingActionButtonEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BookEntryActivity.class);
                startActivity(intent);
            }
        });

        floatingActionButtonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(AdminMainActivity.this,android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
                dialog.setCanceledOnTouchOutside(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.fill_detail_dialog);
                final TextInputEditText cardNo;
                LinearLayout linearLayout,linearLayout1;
                cardNo= dialog.findViewById(R.id.isbn);
                linearLayout1 = dialog.findViewById(R.id.linearLayout2);
                linearLayout = dialog.findViewById(R.id.linearLayout3);
                Button accept = dialog.findViewById(R.id.accept);
                Button cancel = dialog.findViewById(R.id.cancel);
                linearLayout1.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Boolean validation = true;
                        if (cardNo.getText().toString().length()<1) {
                            cardNo.setError("Please enter ISBN");
                            cardNo.requestFocus();
                            validation=false;
                        }
                        if (validation) {
                            databaseReference.child("Book Details").child(cardNo.getText().toString()).child("quantity").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    /*BookObject bookObject = dataSnapshot.getValue(BookObject.class);*/
                                    String quantity1 = String.valueOf(dataSnapshot.getValue());


                                    Log.i("TAF", "NBV " + quantity1);
                                    if (quantity1.equals("null")) {

                                        Toast.makeText(getApplicationContext(), "No ISBN found", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Integer quantity = Integer.valueOf(quantity1);
                                        postQuantity(cardNo.getText().toString(), quantity);
                                        Log.i("TAF", "NBV " + quantity);
                                        cardNo.getText().clear();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getApplicationContext(), ""+databaseError, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        loadFragment(new HomeFragment());
    }

    private void postQuantity(String isbn,Integer qty) {
        String qyty = String.valueOf(qty+1);
        databaseReference.child("Book Details").child(isbn).child("quantity").setValue(qyty);
        Toast.makeText(getApplicationContext(),"Updated Successfuly",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private  void loadFragment(Fragment fragment)
{
    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment).commit();
}
}
