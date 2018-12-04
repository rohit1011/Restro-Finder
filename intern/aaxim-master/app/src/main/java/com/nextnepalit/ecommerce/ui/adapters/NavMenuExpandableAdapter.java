package com.nextnepalit.ecommerce.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextnepalit.ecommerce.R;
import com.nextnepalit.ecommerce.data.models.Categories;
import com.nextnepalit.ecommerce.ui.ItemsListActivity;

import java.util.List;

public class NavMenuExpandableAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private List<Categories> expadableTitles;

    public NavMenuExpandableAdapter(Activity mContext) {
        context = mContext;
    }

    @Override
    public int getGroupCount() {
        if (expadableTitles == null) {
            return 0;
        } else {
            return expadableTitles.size();
        }
    }

    public void setExpadableTitles(List<Categories> expadableTitles) {
        this.expadableTitles = expadableTitles;
        notifyDataSetChanged();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (expadableTitles == null) {
            return 0;
        } else {
            if (expadableTitles.get(groupPosition).getSubCategories() == null) {
                return 0;
            } else {
                return expadableTitles.get(groupPosition).getSubCategories().size();
            }
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return expadableTitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return expadableTitles.get(groupPosition).getSubCategories().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String itemName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater groupInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = groupInflater.inflate(R.layout.expandable_title, null);
        }

        TextView expandableTitleText = convertView.findViewById(R.id.expandableTitleText);
        ImageView expandImage = convertView.findViewById(R.id.expandImage);
        if (expadableTitles.get(groupPosition).getSubCategories() == null || expadableTitles.get(groupPosition).getSubCategories().size() < 1) {
            expandImage.setVisibility(View.GONE);
        } else {
            expandImage.setVisibility(View.VISIBLE);
        }

        if (isExpanded) {
            expandImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_up_black_24dp));
        }
        if (!isExpanded) {
            expandImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_down_black_24dp));
        }

        expandableTitleText.setText(expadableTitles.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childItem = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater childInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = childInflater.inflate(R.layout.layout_expandable_sub, null);
        }
        final TextView expandableTitleText = convertView.findViewById(R.id.expandableSubTitleText);
        expandableTitleText.setText(expadableTitles.get(groupPosition).getSubCategories().get(childPosition).getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemsListIntent = new Intent(context, ItemsListActivity.class);
                itemsListIntent.putExtra("CATID", expadableTitles.get(groupPosition).getSubCategories().get(childPosition).getId());
                itemsListIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(itemsListIntent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
