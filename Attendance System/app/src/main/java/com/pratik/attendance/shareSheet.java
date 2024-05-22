package com.pratik.attendance;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import java.io.File;

public class shareSheet {
    public static void openFolderInFileManager(Context context) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String folderName = "Attendance";
        File folder = new File(path, folderName);
        if (!folder.exists()) {
            Toast.makeText(context, "Folder does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri folderUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Use FileProvider for devices with Android Nougat (API level 24) and higher
            folderUri = FileProvider.getUriForFile(context, "com.pratik.attendance.fileprovider", folder);
        } else {
            folderUri = Uri.fromFile(folder);
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(folderUri, DocumentsContract.Document.MIME_TYPE_DIR);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No file manager app found on your device", Toast.LENGTH_SHORT).show();
        }
    }
}

