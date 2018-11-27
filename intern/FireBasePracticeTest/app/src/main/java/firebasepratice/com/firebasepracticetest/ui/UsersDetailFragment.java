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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import firebasepratice.com.firebasepracticetest.Objects.UserDetailObject;
import firebasepratice.com.firebasepracticetest.R;
import firebasepratice.com.firebasepracticetest.adapters.UserDetailsAdapter;

public class UsersDetailFragment extends Fragment {
    private View mViewFragment;
    private TextView userDetails;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private RecyclerView mRecycleview;
    private  RecyclerView.LayoutManager mLayoutManagerCategory;
    private UserDetailsAdapter userDetailsAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewFragment = inflater.inflate(R.layout.fragment_userdetail,container,false);
        userDetails = mViewFragment.findViewById(R.id.textview_userdetails);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");


        mRecycleview = mViewFragment.findViewById(R.id.userdetail_recycle);
        mLayoutManagerCategory = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecycleview.setLayoutManager(mLayoutManagerCategory);
        mRecycleview.setHasFixedSize(true);
        mRecycleview.setItemViewCacheSize(20);
        mRecycleview.setNestedScrollingEnabled(false);
        mRecycleview.setDrawingCacheEnabled(true);
        mRecycleview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        mRecycleview.setItemAnimator(new DefaultItemAnimator());
        userDetailsAdapter = new UserDetailsAdapter(getActivity().getApplicationContext());
        mRecycleview.setAdapter(userDetailsAdapter);




        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    UserDetailObject userDetailObject = ds.getValue(UserDetailObject.class);
                    Log.i("TAF","CHECKL"+userDetailObject.getEmail());
                    userDetailsAdapter.setUserDetails(userDetailObject);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return mViewFragment;


    }
}
