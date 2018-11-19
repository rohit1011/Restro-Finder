package firebasepratice.com.firebasepracticetest.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import firebasepratice.com.firebasepracticetest.Objects.BookObject;
import firebasepratice.com.firebasepracticetest.R;
import firebasepratice.com.firebasepracticetest.adapters.BioBookAdapter;
import firebasepratice.com.firebasepracticetest.adapters.BooksViewAdapter;
import firebasepratice.com.firebasepracticetest.adapters.ChildBookAdapter;
import firebasepratice.com.firebasepracticetest.adapters.MiscellaneousBookAdapter;

public class HomeFragment extends Fragment {
    private View mViewFragment;
    private ArrayList<String> bookISBNObjects=new ArrayList<>();
    private BookObject bookObject;
    private BooksViewAdapter booksViewAdapter;
    private RecyclerView recyclerView,recyclerViewBiography,recyclerViewChildGenre,recyclerViewOthers,recyclerViewFa;
    private  RecyclerView.LayoutManager mLayoutBiography,mLayoutManagerCategory;
    private BioBookAdapter bioBookAdapter;
    private ChildBookAdapter childBookAdapter;
    private MiscellaneousBookAdapter miscellaneousBookAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewFragment = inflater.inflate(R.layout.fragment_home,container,false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        recyclerView = mViewFragment.findViewById(R.id.recycle_novel);
        mLayoutManagerCategory = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManagerCategory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        booksViewAdapter = new BooksViewAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(booksViewAdapter);


        recyclerViewBiography = mViewFragment.findViewById(R.id.recycle_biography);
        mLayoutBiography = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBiography.setLayoutManager(mLayoutBiography);
        recyclerViewBiography.setHasFixedSize(true);
        recyclerViewBiography.setItemViewCacheSize(20);
        recyclerViewBiography.setDrawingCacheEnabled(true);
        recyclerViewBiography.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerViewBiography.setItemAnimator(new DefaultItemAnimator());
        bioBookAdapter = new BioBookAdapter(getActivity().getApplicationContext());
        recyclerViewBiography.setAdapter(bioBookAdapter);

        recyclerViewChildGenre = mViewFragment.findViewById(R.id.recycle_child_genre);
        mLayoutManagerCategory = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewChildGenre.setLayoutManager(mLayoutManagerCategory);
        recyclerViewChildGenre.setHasFixedSize(true);
        recyclerViewChildGenre.setItemViewCacheSize(20);
        recyclerViewChildGenre.setNestedScrollingEnabled(false);
        recyclerViewChildGenre.setDrawingCacheEnabled(true);
        recyclerViewChildGenre.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerViewChildGenre.setItemAnimator(new DefaultItemAnimator());
        childBookAdapter = new ChildBookAdapter(getActivity().getApplicationContext());
        recyclerViewChildGenre.setAdapter(childBookAdapter);

        recyclerViewOthers = mViewFragment.findViewById(R.id.recycle_others);
        mLayoutManagerCategory = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewOthers.setLayoutManager(mLayoutManagerCategory);
        recyclerViewOthers.setHasFixedSize(true);
        recyclerViewOthers.setItemViewCacheSize(20);
        recyclerViewOthers.setNestedScrollingEnabled(false);
        recyclerViewOthers.setDrawingCacheEnabled(true);
        recyclerViewOthers.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerViewOthers.setItemAnimator(new DefaultItemAnimator());
        miscellaneousBookAdapter = new MiscellaneousBookAdapter(getActivity().getApplicationContext());
        recyclerViewOthers.setAdapter(miscellaneousBookAdapter);



        DatabaseReference myRef = database.getReference("Books");
        myRef.child("Book Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
/*
                BookObject bookObject= dataSnapshot.getValue(BookObject.class);

                Log.d("tmz", "getImg: " + bookObject.getAuthor() + ", getMark_name " + bookObject.getBookName());*/
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.getKey();
                    bookISBNObjects.add(name);
                    bookObject= ds.getValue(BookObject.class);
                    if (bookObject.getGenere().equals("Novel")) {
                        booksViewAdapter.setBookObjects(bookObject);
                        Log.i("FFD", "fdfd" + bookObject.getBookName());
                    }
                    else if (bookObject.getGenere().equals("BioGraphy")){
                        bioBookAdapter.setBioBookObjects(bookObject);
                    }
                    else if (bookObject.getGenere().equals("Child")){
                        childBookAdapter.setChildBook(bookObject);
                    }
                    else {
                        miscellaneousBookAdapter.setMiscellaneousBook(bookObject);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("tmz", "Failed to read value.", error.toException());
            }
        });
        Log.i("TAK","HE"+bookISBNObjects.size());
        for (int i=0;i<bookISBNObjects.size();i++){
            Log.i("TAGG","book ISBN: "+bookISBNObjects.size());
        }

        return mViewFragment;
    }
}
