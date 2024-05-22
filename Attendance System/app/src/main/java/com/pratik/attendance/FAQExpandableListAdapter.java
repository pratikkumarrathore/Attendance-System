package com.pratik.attendance;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.pratik.attendance.R;

import java.util.HashMap;
import java.util.List;

public class FAQExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> faqTitles;
    private HashMap<String, List<String>> faqContent;

    public FAQExpandableListAdapter(Context context, List<String> faqTitles, HashMap<String, List<String>> faqContent) {
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
        final String faqQuestion = (String) getChild(groupPosition, childPosition);
        final String faqAnswer = getAnswerForQuestion(faqQuestion); // Retrieve the answer for the question

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        TextView faqQuestionTextView = convertView.findViewById(R.id.faq_question_text);
        final TextView faqAnswerTextView = convertView.findViewById(R.id.faq_answer_text);

        faqQuestionTextView.setText(faqQuestion);
        faqAnswerTextView.setText(""); // Clear the answer initially

        faqQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (faqAnswerTextView.getVisibility() == View.GONE) {
                    faqAnswerTextView.setText(faqAnswer); // Set the answer
                    faqAnswerTextView.setVisibility(View.VISIBLE); // Show the answer
                } else {
                    faqAnswerTextView.setText(""); // Clear the answer
                    faqAnswerTextView.setVisibility(View.GONE); // Hide the answer
                }
            }
        });

        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // Method to retrieve the answer for a given question
    private String getAnswerForQuestion(String question) {
        // Replace this with your implementation to retrieve the answer for the given question
        // You can store the answers in a separate data structure or fetch them from a database or API
        // For simplicity, let's return dummy answers based on the question

        if (question.equals("What is the purpose of this app?")) {
            return "The purpose of this app is to help teachers to manage student's attendance by just few clicks";
        }
        else if (question.equals("How can I contact support?")) {
            return "You can contact us by email provided in about us page";
        }
        else if (question.equals("What if I sign out or uninstall the app?")) {
            return "If you signed out from the app or uninstall the app then all your data will be lost like classes and students records.So we will suggest to do not sign out if you don't want to lose your data";
        }
        else if (question.equals("How can I add classes to the app?")) {
            return "After logging in, navigate to the Enroll section and tap the Floating button/plus button to add a new class.Click on it and provide the necessary details such as the class name and subject name";
        }
        else if (question.equals("Can I add multiple students to a class?")) {
            return "Yes, you can add multiple students to a class. Once you have created a class, go to the class details page and look for the menu button at top right corner. Click on \"Add Student\" and enter the student's information. Repeat this process for each student you want to add.";
        }
        else if (question.equals("Can I edit the class name or student name?")) {
            return "Yes, you can edit the class name and student name. Long-press on the name of the class or student that you want to change. A menu or options will appear, allowing you to edit the name. Make the necessary modifications and save the changes.";
        }
        else if (question.equals("How do I take attendance for a class?")) {
            return "To take attendance, go to the class details page and You will see a list of students enrolled in the class. To mark a student as present, click on their name once. To mark them as absent, click on their name twice.To save the attendance details just tap the save button on the toolbar";
        }
        else if (question.equals("Can I view attendance history?")) {
            return "Yes you can view the attendance history by navigating to that particular date and you can view the attendance sheet by navigating to the \"attendance sheet\" in menu option ";
        }
        else if (question.equals("Can I change the student's status after saving it?")) {
            return "Yes you can change student's status of any date even after saving it.To do that, go to class details then change the date in which you want to change status then simply change the status of that student and don't forget to save it.";
        }
        else if (question.equals("How can I save the attendance details?")) {
            return "After taking the attendance just tap the \"save button\" on the toolbar to save the attendance.";
        }
        else if (question.equals("Can I change the date to take the attendance?")) {
            return "Yes you can change date in menu option at the top right corner to take the attendance of that date.";
        }
        else if (question.equals("Can I generate an attendance sheet for a specific month?")) {
            return "Yes, you can generate an attendance sheet filtered by month. Go to the class details page and find the attendance section. Look for the \"Attendance Sheet\". Click on it and select the desired month. The app will generate the attendance sheet for that month.";
        }
        else if (question.equals("How can I download the attendance sheet?")) {
            return "After generating the attendance sheet, you will have the option to download it. Click on the \"save button\". Allow the requested permission then the app will save the attendance sheet in your phone's storage, typically in the \"Downloads\" folder.";
        }
        else if (question.equals("Can I delete the class or any student record")) {
            return "To delete any record, long press on that particular record then simply press \"delete\"";
        }


        return "Answer not found.";
    }
}
