package firebasepratice.com.firebasepracticetest.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import firebasepratice.com.firebasepracticetest.R;

public class UsersDetailFragment extends Fragment {
    private View mViewFragment;
    private TextView userDetails;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewFragment = inflater.inflate(R.layout.fragment_userdetail,container,false);
        userDetails = mViewFragment.findViewById(R.id.textview_userdetails);
        return mViewFragment;
    }
}
