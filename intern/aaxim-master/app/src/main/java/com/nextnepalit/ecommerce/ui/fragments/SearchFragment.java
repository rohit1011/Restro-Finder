package com.nextnepalit.ecommerce.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nextnepalit.ecommerce.R;
import com.nextnepalit.ecommerce.viewmodels.SearchViewModel;

public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;
    RecyclerView search_recycler_view;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.search_fragment, container, false);
        search_recycler_view = view.findViewById(R.id.search_recycler_view);
        search_recycler_view.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View viewRecycler = LayoutInflater.from(getContext()).inflate(R.layout.category_search_items, viewGroup, false);

                return new ViewHolder(viewRecycler);
            }

            class ViewHolder extends RecyclerView.ViewHolder {
                ViewHolder(@NonNull View itemView) {
                    super(itemView);
                }
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            }

            @Override
            public int getItemCount() {
                return 10;
            }

        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        // TODO: Use the ViewModel
    }

}
