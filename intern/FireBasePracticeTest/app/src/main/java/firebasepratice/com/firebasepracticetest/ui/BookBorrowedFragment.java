package firebasepratice.com.firebasepracticetest.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import firebasepratice.com.firebasepracticetest.Objects.BookEntryObject;
import firebasepratice.com.firebasepracticetest.R;
import firebasepratice.com.firebasepracticetest.adapters.BookBorrowedAdapter;

public class BookBorrowedFragment extends Fragment {
private View mViewFragment;
private RecyclerView recyclerViewBookEntry;
private RecyclerView.LayoutManager mLayoutManagerCategory;
private BookBorrowedAdapter bookBorrowedAdapter;
private FirebaseDatabase firebaseDatabase;
private DatabaseReference databaseReference;
private BookEntryObject bookEntryObject;
private ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewFragment =inflater.inflate(R.layout.fragment_book_borrowed,container,false);
        recyclerViewBookEntry = mViewFragment.findViewById(R.id.recycle_entry);
        mLayoutManagerCategory = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewBookEntry.setLayoutManager(mLayoutManagerCategory);
        recyclerViewBookEntry.setHasFixedSize(true);
        recyclerViewBookEntry.setItemViewCacheSize(20);
        recyclerViewBookEntry.setNestedScrollingEnabled(false);
        recyclerViewBookEntry.setDrawingCacheEnabled(true);
        recyclerViewBookEntry.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerViewBookEntry.setItemAnimator(new DefaultItemAnimator());
        bookBorrowedAdapter = new BookBorrowedAdapter(getActivity().getApplicationContext());
        recyclerViewBookEntry.setAdapter(bookBorrowedAdapter);
        firebaseDatabase = FirebaseDatabase.getInstance();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 2000);

        databaseReference= firebaseDatabase.getReference("Book Entry");
        databaseReference.child("Entries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    bookEntryObject = ds.getValue(BookEntryObject.class);
                    bookBorrowedAdapter.setBookEntry(bookEntryObject);
                     }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        return mViewFragment;

        
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        progressDialog = new ProgressDialog(getActivity().getApplicationContext());
        progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        progressDialog.show();
    }

}
