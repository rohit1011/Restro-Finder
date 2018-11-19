package firebasepratice.com.firebasepracticetest.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import firebasepratice.com.firebasepracticetest.R;
import firebasepratice.com.firebasepracticetest.adapters.BookBorrowedAdapter;

public class BookBorrowedFragment extends Fragment {
private View mViewFragment;
private RecyclerView recyclerViewBookEntry;
private RecyclerView.LayoutManager mLayoutManagerCategory;
private BookBorrowedAdapter bookBorrowedAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewFragment =inflater.inflate(R.layout.fragment_book_borrowed,container,false);



        recyclerViewBookEntry = mViewFragment.findViewById(R.id.recycle_entry);
        mLayoutManagerCategory = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBookEntry.setLayoutManager(mLayoutManagerCategory);
        recyclerViewBookEntry.setHasFixedSize(true);
        recyclerViewBookEntry.setItemViewCacheSize(20);
        recyclerViewBookEntry.setNestedScrollingEnabled(false);
        recyclerViewBookEntry.setDrawingCacheEnabled(true);
        recyclerViewBookEntry.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerViewBookEntry.setItemAnimator(new DefaultItemAnimator());
        bookBorrowedAdapter = new BookBorrowedAdapter(getActivity().getApplicationContext());
        recyclerViewBookEntry.setAdapter(bookBorrowedAdapter);

        return mViewFragment;
        
    }
}
