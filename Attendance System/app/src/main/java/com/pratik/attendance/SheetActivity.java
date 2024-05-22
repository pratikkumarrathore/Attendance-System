package com.pratik.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

public class SheetActivity extends AppCompatActivity {

    Toolbar toolbar;
    private String month;
    private String fileName=null;
    private TextView subtitle;
    private static final int REQUEST_MANAGE_EXTERNAL_STORAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet);

        // Check for storage permission and request if not granted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


        Intent intent = getIntent();
        month = intent.getStringExtra("month");
        setToolBar();

        showTable();
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar2);
        TextView title = toolbar.findViewById(R.id.title_toolbar2);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar2);
        ImageButton download = toolbar.findViewById(R.id.download);
        ImageButton back = toolbar.findViewById(R.id.back2);


        title.setText(month);
        subtitle.setVisibility(View.GONE);
        download.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                requestManageExternalStoragePermission();
            } else {
                saveTableAsPdf();
            }
        });

        back.setOnClickListener(view -> onBackPressed());

    }



    private void showTable() {
        DbHelper dbHelper = new DbHelper(this);
        TableLayout tableLayout =  findViewById(R.id.tableLayout);
        long[] idArray= getIntent().getLongArrayExtra("idArray");
        int[] rollArray = getIntent().getIntArrayExtra("rollArray");
        String[] nameArray = getIntent().getStringArrayExtra("nameArray");
        String month = getIntent().getStringExtra("month");

        int DAY_IN_MONTH = getDayInMonth(month);

        //row setup
        int rowSize=idArray.length+1;
        TableRow[] rows = new TableRow[rowSize];
        TextView[] roll_tvs = new TextView[rowSize];
        TextView[] name_tvs = new TextView[rowSize];
        TextView[][] status_tvs = new TextView[rowSize][DAY_IN_MONTH+1];

        for (int i =0; i<rowSize;i++){
            roll_tvs[i] = new TextView(this);
            name_tvs[i] = new TextView(this);
            for (int j = 1; j <= DAY_IN_MONTH; j++) {
                status_tvs[i][j]=new TextView(this);
            }
        }

        //header
        roll_tvs[0].setText("RollNo");
        roll_tvs[0].setTypeface(roll_tvs[0].getTypeface(), Typeface.BOLD);
        name_tvs[0].setText("Name");
        name_tvs[0].setTypeface(name_tvs[0].getTypeface(), Typeface.BOLD);
        for (int i = 1; i <= DAY_IN_MONTH; i++) {
            status_tvs[0][i].setText(String.valueOf(i));
            status_tvs[0][i].setTypeface(status_tvs[0][i].getTypeface(), Typeface.BOLD);
        }

        for (int i = 1; i < rowSize; i++) {
            roll_tvs[i].setText(String.valueOf(rollArray[i-1]));
            name_tvs[i].setText(nameArray[i-1]);

            for (int j = 1; j <= DAY_IN_MONTH; j++) {
                String day = String.valueOf(j);
                if (day.length()==1) day= "0"+day;
                String date = day+"."+month;  //11.06.2023  where 11 is day and 06.2023  is month
                String status = dbHelper.getStatus(idArray[i-1],date);
                status_tvs[i][j].setText(status);

                // Color change

                if (status != null) {
                    if (status.equals("A")) {
                        status_tvs[i][j].setTextColor(Color.RED); // Set text color to red for "A"
                    } else if (status.equals("P")) {
                        status_tvs[i][j].setTextColor(Color.parseColor("#FF34BF39")); // Set text color to green for "P"
                    }
                }
            }
        }

        for (int i = 0; i < rowSize; i++) {
            rows[i] = new TableRow(this);
            if(i%2==0)
                rows[i].setBackgroundColor(Color.parseColor("#FFEAE4E4"));
            else
                rows[i].setBackgroundColor(Color.parseColor("#FF989595"));


            roll_tvs[i].setPadding(16,16,16,16);
            name_tvs[i].setPadding(16,16,16,16);

            rows[i].addView(roll_tvs[i]);
            rows[i].addView(name_tvs[i]);

            for (int j = 1; j <= DAY_IN_MONTH; j++) {
                status_tvs[i][j].setPadding(16,16,16,16);

                rows[i].addView(status_tvs[i][j]);
            }

            tableLayout.addView(rows[i]);
        }
        tableLayout.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);
    }

    private void saveTableAsPdf() {
        // Create a file to save the PDF
       // File pdfFile = new File(Environment.getExternalStorageDirectory(), "table.pdf");

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        // Make Folder
        String folderName = "Attendance";
        File folder = new File(path, folderName);
        if (!folder.exists()) {
            folder.mkdirs(); // Create the folder if it doesn't exist
        }
        // Make Folder

        //Toast.makeText(this, "PDF saved to Phone storage/Downloads", Toast.LENGTH_SHORT).show();
        String pdfFile = path.getAbsolutePath()+"/"+month+"attendance.pdf";

        try {
            // Create a PDF document
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            // Create a table with the same number of columns as the table layout
            TableLayout tableLayout = findViewById(R.id.tableLayout);
            int numColumns = 33;
            PdfPTable table = new PdfPTable(numColumns);

            table.setWidthPercentage(100); // Set table width to 100% of page width

            // Set column widths
            float[] columnWidths = new float[numColumns];
            columnWidths[0] = 1f; // Adjust the width of the first column as needed
            columnWidths[1] = 1f; // Adjust the width of the second column as needed
            for (int i = 2; i < numColumns; i++) {
                columnWidths[i] = 0.5f; // Adjust the width of remaining columns as needed
            }
            table.setWidths(columnWidths);

            // Add the header row to the table
            TableRow headerRow = (TableRow) tableLayout.getChildAt(0);
            for (int i = 0; i < numColumns; i++) {
                TextView headerTextView = (TextView) headerRow.getChildAt(i);
                PdfPCell headerCell = new PdfPCell(new Phrase(headerTextView.getText().toString()));
                headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY); // Set header row background color
                headerCell.setPhrase(new Phrase(headerTextView.getText().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD))); // Set header row font and style
                table.addCell(headerCell);
            }

            // Add the remaining rows to the table
            int numRows = tableLayout.getChildCount();
            for (int i = 1; i < numRows; i++) {
                TableRow row = (TableRow) tableLayout.getChildAt(i);
                for (int j = 0; j < numColumns; j++) {
                    TextView textView = (TextView) row.getChildAt(j);
                    PdfPCell cell = new PdfPCell(new Phrase(textView.getText().toString()));
                    table.addCell(cell);
                }
            }


            // Set font size for table cells
            Font cellFont = new Font(Font.FontFamily.HELVETICA, 5);
            for (PdfPRow pdfPRow : table.getRows()) {
                for (PdfPCell pdfPCell : pdfPRow.getCells()) {
                    pdfPCell.setPadding(2); // Adjust cell padding as needed
                    pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setPhrase(new Phrase(pdfPCell.getPhrase().getContent(), cellFont));
                }
            }

            // Add the table to the document
            document.add(table);

            // Close the document
            document.close();

            // Show a toast message indicating the PDF file was saved successfully
            Toast.makeText(this, "PDF saved to internal storage/Downloads", Toast.LENGTH_SHORT).show();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save PDF", Toast.LENGTH_SHORT).show();
        }
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, you can now save the PDF
            saveTableAsPdf();
        } else {
            Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestManageExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, REQUEST_MANAGE_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MANAGE_EXTERNAL_STORAGE) {
            // Check if the user has granted the permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // Permission granted, proceed with saving the PDF
                    saveTableAsPdf();
                } else {
                    // Permission denied, show a toast or handle accordingly
                    Toast.makeText(this, "Permission denied. Cannot save PDF.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }






    private int getDayInMonth(String month) {
        int monthIndex = Integer.valueOf(month.substring(0,1));
        int year = Integer.valueOf(month.substring(4));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,monthIndex);
        calendar.set(Calendar.YEAR,year);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

}
