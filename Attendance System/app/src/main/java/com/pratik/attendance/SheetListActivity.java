package com.pratik.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SheetListActivity extends AppCompatActivity {

    private ListView sheetList;
    Toolbar toolbar;
    private ArrayAdapter adapter;
    private ArrayList<String> listItems = new ArrayList();
    private long cid;
    private TextView subtitle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_list);

        setToolBar();

        cid=getIntent().getLongExtra("cid",-1);
        loadListItems();
        sheetList=findViewById(R.id.sheetList);
        adapter = new ArrayAdapter(this,R.layout.sheet_list,R.id.date_list_item,listItems);
        sheetList.setAdapter(adapter);

        sheetList.setOnItemClickListener((parent, view, position, id) -> openSheetActivity(position));

    }

    private void openSheetActivity(int position) {
        long[] idArray= getIntent().getLongArrayExtra("idArray");
        int[] rollArray = getIntent().getIntArrayExtra("rollArray");
        String[] nameArray = getIntent().getStringArrayExtra("nameArray");
        Intent intent = new Intent(this,SheetActivity.class);
        intent.putExtra("idArray",idArray);
        intent.putExtra("rollArray",rollArray);
        intent.putExtra("nameArray",nameArray);
        intent.putExtra("month",listItems.get(position));

        startActivity(intent);
    }

    private void setToolBar() {

        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton save = toolbar.findViewById(R.id.save);
        ImageButton back = toolbar.findViewById(R.id.back);

        save.setVisibility(View.GONE);
        title.setText("Attendance Sheet");
        subtitle.setVisibility(View.GONE);
        back.setOnClickListener(view -> onBackPressed());

    }

    private void loadListItems() {
        Cursor cursor=new DbHelper(this).getDistinctMonths(cid);

        while(cursor.moveToNext()){
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DbHelper.DATE_KEY));//01.06.2023
            listItems.add(date.substring(3));
        }
    }
}