package firebasepratice.com.firebasepracticetest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {
private Button admin,user;
private ImageView logoView;
String TAG = "Firebasecheck";
private EditText name,address,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        admin = findViewById(R.id.btn_admin);
        user = findViewById(R.id.btn_user);
        logoView = findViewById(R.id.logoyala);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String loginValue = preferences.getString("LoginValue", "0");
        if (loginValue.equals("user")){
            Intent intent = new Intent(getApplicationContext(),UserMainActivity.class);
            startActivity(intent);
        }
        else{

        }
        Glide.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/fir-test-25367.appspot.com/o/logo%2Fback.png?alt=media&token=aeb96504-e426-4344-b1fd-e85459461465")
                .into(logoView);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AdminLoginActivity.class);
                startActivity(intent);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(),UserLoginActivity.class);
        startActivity(intent);
            }
            });
         }

}
