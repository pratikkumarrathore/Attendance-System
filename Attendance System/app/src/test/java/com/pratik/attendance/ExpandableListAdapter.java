package com.pratik.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> faqTitles;
    private HashMap<String, List<String>> faqContent;

    public ExpandableListAdapter(Context context, List<String> faqTitles, HashMap<String, List<String>> faqContent) {
        this.context = context;
        this.faqTitles = faqTitles;
        this.faqContent = faqContent;
    }

    @Override
    public int getGroupCount() {
        return faqTitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return faqContent.get(faqTitles.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return faqTitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return faqContent.get(faqTitles.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String faqTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView faqTitleTextView = convertView.findViewById(R.id.faq_title_text);
        faqTitleTextView.setText(faqTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String faqQuestion = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        TextView faqQuestionTextView = convertView.findViewById(R.id.faq_question_text);
        faqQuestionTextView.setText(faqQuestion);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
