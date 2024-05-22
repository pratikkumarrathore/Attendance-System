package com.pratik.attendance;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FAQ extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> faqTitles;
    private HashMap<String, List<String>> faqContent;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        expandableListView = findViewById(R.id.expandableListView);

        // Initialize the FAQ data
        initializeFAQData();

        // Create an instance of the ExpandableListAdapter
        expandableListAdapter = new FAQExpandableListAdapter(this, faqTitles, faqContent);

        // Set the adapter for the ExpandableListView
        expandableListView.setAdapter(expandableListAdapter);

        // Set the OnChildClickListener for the ExpandableListView
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // Get the clicked question and show the answer
                String question = (String) expandableListAdapter.getChild(groupPosition, childPosition);
                String answer = getAnswerForQuestion(question);

                // Display the answer in a toast message
                Toast.makeText(FAQ.this, answer, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        setToolBar();
    }
    private void setToolBar() {

        //ToolBar Code
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.save);

        title.setText("FAQ");
        subtitle.setVisibility(View.GONE);
        back.setOnClickListener(view -> onBackPressed());
        save.setVisibility(View.GONE);
    }


    // Method to initialize the FAQ data
    private void initializeFAQData() {
        // Initialize the faqTitles and faqContent
        faqTitles = new ArrayList<>();
        faqContent = new HashMap<>();

        // Add faq categories
        faqTitles.add("General");
        faqTitles.add("Attendance");

        // Add faq questions and answers for each category
        List<String> generalQuestions = new ArrayList<>();
        generalQuestions.add("What is the purpose of this app?");
        generalQuestions.add("How can I contact support?");
        generalQuestions.add("What if I sign out or uninstall the app?");

        List<String> attendanceQuestions = new ArrayList<>();
        attendanceQuestions.add("How can I add classes to the app?");
        attendanceQuestions.add("Can I add multiple students to a class?");
        attendanceQuestions.add("How do I take attendance for a class?");
        attendanceQuestions.add("How can I save the attendance details?");
        attendanceQuestions.add("Can I change the date to take the attendance?");
        attendanceQuestions.add("Can I view attendance history?");
        attendanceQuestions.add("Can I change the student's status after saving it?");
        attendanceQuestions.add("Can I generate an attendance sheet for a specific month?");
        attendanceQuestions.add("How can I download the attendance sheet?");
        attendanceQuestions.add("Can I edit the class name or student name?");


        faqContent.put(faqTitles.get(0), generalQuestions);
        faqContent.put(faqTitles.get(1), attendanceQuestions);
    }

    // Method to get the answer for a given question
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
            return " After logging in, navigate to the Enroll section and tap the Floating button/plus button to add a new class.Click on it and provide the necessary details such as the class name and subject name";
        }
        else if (question.equals("Can I add multiple students to a class?")) {
            return " Yes, you can add multiple students to a class. Once you have created a class, go to the class details page and look for the menu button at top right corner. Click on \"Add Student\" and enter the student's information. Repeat this process for each student you want to add.";
        }
        else if (question.equals("Can I edit the class name or student name?")) {
            return " Yes, you can edit the class name and student name. Long-press on the name of the class or student that you want to change. A menu or options will appear, allowing you to edit the name. Make the necessary modifications and save the changes.";
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
            return "After taking the attendance just tap the save button on the toolbar to save the attendance.";
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

        return "Answer Not found";
    }
}
