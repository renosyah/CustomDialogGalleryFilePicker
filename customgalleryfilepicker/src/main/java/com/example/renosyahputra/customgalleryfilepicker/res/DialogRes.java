package com.example.renosyahputra.customgalleryfilepicker.res;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.example.renosyahputra.customgalleryfilepicker.res.obj.GalleryFileObj;

import java.io.File;
import java.util.ArrayList;

public class DialogRes {
    public static ArrayList<GalleryFileObj> GetAllGalleryFile(Context context){
        ArrayList<GalleryFileObj> datas = new ArrayList<>();

        String[] columns = {"*"};

        String orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";

        Cursor phones = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        assert phones != null;
        while (phones.moveToNext()) {
            Uri path = Uri.parse(phones.getString(phones.getColumnIndex(MediaStore.Images.Media.DATA)));

            datas.add(new GalleryFileObj(new File("" + path.getPath()).getName(), path.getPath()));

        }
        phones.close();

        return datas;
    }


    public static Boolean RequestPermissionReadStorageIfNotGranted(final Context ctx){
        Boolean NotGranted = ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        if (NotGranted){
            new AlertDialog.Builder(ctx)
                    .setTitle("Meminta Izin")
                    .setMessage("Meminta Izin melihat data penyimpanan anda")
                    .setPositiveButton("Izinkan", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(((Activity)ctx),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},15);
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
        return NotGranted;
    }
}
