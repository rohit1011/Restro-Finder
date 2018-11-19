package firebasepratice.com.firebasepracticetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
private Button save,retrive;
String TAG = "Firebasecheck";
private EditText name,address,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        save = findViewById(R.id.save);
        retrive = findViewById(R.id.retrive);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        url = findViewById(R.id.url);



        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("room details");
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        final EscapeRoom escapeRoom = new EscapeRoom(name.getText().toString(),address.getText().toString(),url.getText().toString());
       myRef.child("rooms").push().setValue(escapeRoom);
    }
});

retrive.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                TextView textView= findViewById(R.id.textview);
                textView.setText(""+value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
});
    }
}
