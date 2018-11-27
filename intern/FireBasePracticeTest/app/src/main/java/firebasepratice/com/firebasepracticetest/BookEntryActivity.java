package firebasepratice.com.firebasepracticetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import firebasepratice.com.firebasepracticetest.Objects.BookEntryObject;
import firebasepratice.com.firebasepracticetest.Objects.BookObject;
import firebasepratice.com.firebasepracticetest.Objects.UserBookBorrowDetails;
import firebasepratice.com.firebasepracticetest.Objects.UserBookEntryDetailsObject;

public class BookEntryActivity extends AppCompatActivity {
    private TextInputEditText ISBN, name, cardNumber, phoneNumber;
    private Button accept, cancel;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference admindb, userDb,userentry;
    private StorageReference storageReference;
    private Integer quantity, getCheck;
    private Boolean lek = false, quantityCheck = false;
    private Boolean validation = true;
    String formattedDate,output,bookName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_entry);
        ISBN = findViewById(R.id.isbn);
        name = findViewById(R.id.borrower_name);
        cardNumber = findViewById(R.id.card_number);
        phoneNumber = findViewById(R.id.phone_number);
        accept = findViewById(R.id.accept);
        cancel = findViewById(R.id.cancel);

        firebaseDatabase = FirebaseDatabase.getInstance();
        admindb = firebaseDatabase.getReference("Book Entry");
        userDb = firebaseDatabase.getReference("Books");
        userentry = firebaseDatabase.getReference("UsersBookBorrowed");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "OnClick Running");
                if (ISBN.getText().toString().length() < 1) {
                    ISBN.setError("ISBN Required");
                    ISBN.requestFocus();
                    validation = false;
                }
                if (name.getText().toString().length() < 1) {
                    name.setError("User Name Required");
                    name.requestFocus();
                    validation = false;
                }
                if (phoneNumber.getText().toString().length() < 1) {
                    phoneNumber.setError("Phone number Required");
                    phoneNumber.requestFocus();
                    validation = false;
                }
                if (cardNumber.getText().toString().length() < 1) {
                    cardNumber.setError("Card Number Required");
                    cardNumber.requestFocus();
                    validation = false;
                }
                if (validation) {
                    uploadImage();
                }

            }
        });
cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(),AdminMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
});

    }

    private void uploadImage() {
        getdate();
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    BookObject bookObject = dataSnapshot.child("Book Details").child(ISBN.getText().toString()).getValue(BookObject.class);
                    quantity = Integer.parseInt(bookObject.getQuantity());
                    if (quantity > 0) {
                        bookfetch();
                    } else {
                        Toast.makeText(getApplicationContext(), "Requested book not available", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "ISBN not found", Toast.LENGTH_SHORT).show();
                    ISBN.getText().clear();
                    ISBN.requestFocus();
                    ISBN.setError("ISBN not found");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("GAAAAA", "fail");

            }
        });
    }

    private void bookfetch() {
        quantityCheck = true;
        BookEntryObject bookEntryObject = new BookEntryObject(ISBN.getText().toString()
                , name.getText().toString()
                , cardNumber.getText().toString()
                , phoneNumber.getText().toString()
                , formattedDate
                , output);
        admindb.child("Entries").child(cardNumber.getText().toString()).setValue(bookEntryObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        start();
                    }
                });
    }

    private void entry(String book_name) {
        Log.i("TAAAAG","checkmessage"+bookName);
        UserBookBorrowDetails userBookBorrowDetails = new UserBookBorrowDetails(book_name
                , name.getText().toString()
                , cardNumber.getText().toString()
                , phoneNumber.getText().toString()
                , formattedDate
                , output);
        userentry.child("Entries").child(cardNumber.getText().toString()).child(formattedDate).setValue(userBookBorrowDetails);
        ISBN.getText().clear();
        name.getText().clear();
        cardNumber.getText().clear();
        phoneNumber.getText().clear();
        ISBN.requestFocus();
    }

    private void start() {

        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), "SUCESS", Toast.LENGTH_SHORT).show();
                BookObject bookObject = dataSnapshot.child("Book Details").child(ISBN.getText().toString()).getValue(BookObject.class);
                bookName= bookObject.getBookName();
                quantity = Integer.parseInt(bookObject.getQuantity());
                getCheck = quantity - 1;
                userDb.child("Book Details").child(ISBN.getText().toString()).child("quantity").setValue(getCheck.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        entry(bookName);
                    }
                });
                        }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void getdate() {
            Date cl = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
           formattedDate = df.format(cl);

            String dt = formattedDate;  // Start date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(dt));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 8);  // number of d11ays to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
            output = sdf1.format(c.getTime());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),AdminMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
