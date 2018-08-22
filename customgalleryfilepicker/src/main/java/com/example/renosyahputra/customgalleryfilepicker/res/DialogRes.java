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
import com.example.renosyahputra.customgalleryfilepicker.res.obj.lang.ActivityGalleryFilePickerLang;

import java.io.File;
import java.util.ArrayList;

public class DialogRes {
    
    public static void SetIndoLang(ActivityGalleryFilePickerLang lang){
        lang.title = "Pilih";
        lang.dialogTitle = "Pilih Gambar";
        lang.dialogBatal = "Batal";

        lang.TitleFileType = "Pilih Tipe";

        lang.titleSelectImage = "Gambar";
        lang.titleSelectMusic = "Musik";
        lang.titleSelectVideo = "Video";
        lang.titleSelectPDF = "PDF";
        lang.titleSelectZip = "Zip";
        lang.titleSelectOther = "File Lainnya";

        lang.fileEmpty = "Penyimpanan Kosong";
        lang.fileNotFound = "File Tidak Ketemu";

        lang.titlePermission = "Meminta Izin";
        lang.messagePermission = "aplikasi ingin Mengakses Penyimpanan di perangkat anda";
        lang.notGranted = "Tolak";
        lang.granted = "Izinkan";
    }
    
    public static void SetEnglishLang(ActivityGalleryFilePickerLang lang){
        lang.title = "Choose";
        lang.dialogTitle = "Choose Image";
        lang.dialogBatal = "Cancel";

        lang.TitleFileType = "Select Type";

        lang.titleSelectImage = "Image";
        lang.titleSelectMusic = "Music";
        lang.titleSelectVideo = "Video";
        lang.titleSelectPDF = "PDF";
        lang.titleSelectZip = "Zip";
        lang.titleSelectOther = "Other File";

        lang.fileEmpty = "Storage Is Empty";
        lang.fileNotFound = "File not found";

        lang.titlePermission = "Request Permission";
        lang.messagePermission = "App want to acces your device's storage";
        lang.notGranted = "Not Allow";
        lang.granted = "Granted";
    }
    
    public static ArrayList<GalleryFileObj> GetAllGalleryFile(Context context){
        ArrayList<GalleryFileObj> datas = new ArrayList<>();

        String[] columns = {"*"};

        String orderBy = MediaStore.Files.FileColumns.DATE_ADDED+" DESC";
        String select =  MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_NONE;

        Cursor phones = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), columns, select, null, orderBy);
        assert phones != null;
        while (phones.moveToNext()) {
            Uri path = Uri.parse(phones.getString(phones.getColumnIndex(MediaStore.Files.FileColumns.DATA)));
            datas.add(new GalleryFileObj(new File(path.getPath()).getName(), path.getPath()));
        }
        phones.close();

        return datas;
    }
    public static ArrayList<GalleryFileObj> GetAllGalleryAudio(Context context){
        ArrayList<GalleryFileObj> datas = new ArrayList<>();

        String[] columns = {"*"};

        String orderBy = MediaStore.Audio.Media.DATE_ADDED+" DESC";

        Cursor phones = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        assert phones != null;
        while (phones.moveToNext()) {
            Uri path = Uri.parse(phones.getString(phones.getColumnIndex(MediaStore.Audio.Media.DATA)));
            datas.add(new GalleryFileObj(new File(path.getPath()).getName(), path.getPath()));
        }
        phones.close();

        return datas;
    }
    public static ArrayList<GalleryFileObj> GetAllGalleryVideo(Context context){
        ArrayList<GalleryFileObj> datas = new ArrayList<>();

        String[] columns = {"*"};

        String orderBy = MediaStore.Video.Media.DATE_ADDED+" DESC";

        Cursor phones = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        assert phones != null;
        while (phones.moveToNext()) {
            Uri path = Uri.parse(phones.getString(phones.getColumnIndex(MediaStore.Video.Media.DATA)));

            datas.add(new GalleryFileObj(new File(path.getPath()).getName(), path.getPath()));

        }
        phones.close();

        return datas;
    }
    public static Boolean RequestPermissionReadStorageIfNotGranted(final Context ctx, ActivityGalleryFilePickerLang lang){
        Boolean NotGranted = ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        if (NotGranted){
            new AlertDialog.Builder(ctx)
                    .setTitle(lang.titlePermission)
                    .setMessage(lang.messagePermission)
                    .setPositiveButton(lang.granted, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(((Activity)ctx),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},15);
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton(lang.notGranted, new DialogInterface.OnClickListener() {
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
