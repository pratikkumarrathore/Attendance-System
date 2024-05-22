package com.pratik.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class enroll extends AppCompatActivity {

    FloatingActionButton fab;

    RecyclerView recyclerView;

    ClassAdapter classadapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Class_Item> classItems = new ArrayList<>();

    Toolbar toolbar;
    DbHelper dbHelper;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        dbHelper = new DbHelper(this);

        fab = findViewById(R.id.fab_enroll);
        fab.setOnClickListener(view -> showDialog());

        loadData();

        recyclerView = findViewById(R.id.enroll_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        classadapter = new ClassAdapter(this, classItems);
        recyclerView.setAdapter(classadapter);
        classadapter.setOnItemClickListener(position -> gotoItemActivity(position));

        setToolBar();
    }

    private void loadData() {
        Cursor cursor = dbHelper.getClassTable();

        classItems.clear();
        while (cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DbHelper.C_ID));
            @SuppressLint("Range") String className = cursor.getString(cursor.getColumnIndex(DbHelper.CLASS_NAME_KEY));
            @SuppressLint("Range") String subjectName = cursor.getString(cursor.getColumnIndex(DbHelper.SUBJECT_NAME_KEY));

            classItems.add(new Class_Item(id,className,subjectName));
        }
    }

    private void setToolBar() {

        //ToolBar Code
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.save);

        title.setText("Class");
        subtitle.setVisibility(View.GONE);
        back.setOnClickListener(view -> onBackPressed());
        save.setVisibility(View.INVISIBLE);
    }

    private void gotoItemActivity(int position) {
        Intent intent = new Intent(this, StudentActivity.class);

        intent.putExtra("className", classItems.get(position).getClassName());
        intent.putExtra("subjectName", classItems.get(position).getSubjectName());
        intent.putExtra("position", position);
        intent.putExtra("cid", classItems.get(position).getCid());
        startActivity(intent);
    }

    @SuppressLint("MissingInflatedId")
    private void showDialog() {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.CLASS_ADD_DIALOG);
        dialog.setListener((className, subjectName) -> addClass(className, subjectName));
    }

    private void addClass(String className, String subjectName) {
        //add data to database
        long cid= dbHelper.addClass(className,subjectName);
        Class_Item class_item = new Class_Item(cid, className, subjectName);
        classItems.add(class_item);
        classadapter.notifyDataSetChanged();


    }
    //context manu action method
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 0:
                showUpdateDialog(item.getGroupId());
            case 1:
                deleteClass(item.getGroupId());
        }

        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int position) {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(),MyDialog.CLASS_UPDATE_DIALOG);
        dialog.setListener((className,subjectName)->updateClass(position,className,subjectName));

    }

    private void updateClass(int position, String className, String subjectName) {
        dbHelper.updateClass(classItems.get(position).getCid(),className,subjectName);
        classItems.get(position).setClassName(className);
        classItems.get(position).setSubjectName(subjectName);
        classadapter.notifyItemChanged(position);
    }

    private void deleteClass(int position) {
        dbHelper.deleteClass(classItems.get(position).getCid());
        classItems.remove(position);
        classadapter.notifyItemRemoved(position);
    }
}