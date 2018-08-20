package com.example.renosyahputra.customgalleryfilepicker;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.renosyahputra.customgalleryfilepicker.res.adapter.CustomAdapterGalleryFile;
import com.example.renosyahputra.customgalleryfilepicker.res.interfaceClass.ShowDialog;
import com.example.renosyahputra.customgalleryfilepicker.res.obj.GalleryFileObj;

import java.io.File;
import java.util.ArrayList;

import static com.example.renosyahputra.customgalleryfilepicker.res.DialogRes.GetAllGalleryFile;
import static com.example.renosyahputra.customgalleryfilepicker.res.DialogRes.RequestPermissionReadStorageIfNotGranted;

public class CustomDialogGalleryFilePicker {

    private Context context;
    private ShowDialog.OnShowMyFileListener showDialogListener;
    private AlertDialog dialog;
    private int color = 0;
    private ArrayList<GalleryFileObj> fileDatas;

    public CustomDialogGalleryFilePicker(@NonNull Context context) {
        this.context = context;
    }

    public void SetOnShowDialog(ShowDialog.OnShowMyFileListener showDialogListener){
        this.showDialogListener = showDialogListener;
    }

    public void SetTheme(int color){
        this.color = color;
    }

    private void InitiationDialog(){

        fileDatas = GetAllGalleryFile(context);

        LayoutInflater inflater = ((Activity)this.context).getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_alert_dialog_file_picker,null);

        dialog = new AlertDialog.Builder(context)
                .setNegativeButton("Tutup", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();

        TextView title = v.findViewById(R.id.barTitle);
        title.setText("Pilih File");

        RelativeLayout bar = v.findViewById(R.id.bar);
        if (color != 0){
            bar.setBackgroundColor(color);
        }

        GridView gridViewFile = v.findViewById(R.id.GridViewGalleryFile);
        CustomAdapterGalleryFile adapter = new CustomAdapterGalleryFile(context,R.layout.custom_adapter_gallery_file,fileDatas);
        if (color != 0){
           adapter.SetColor(color);
        }
        gridViewFile.setAdapter(adapter);
        gridViewFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (fileDatas != null && fileDatas.size() > 0 && showDialogListener != null) {
                    GalleryFileObj data = fileDatas.get(i);
                    showDialogListener.OnChoosedFile(data.GetFileName(),new File(data.Path));
                }
                dialog.dismiss();
            }
        });

        dialog.setView(v);

    }

    public void ShowDialog(){

        if (RequestPermissionReadStorageIfNotGranted(context)){
            return;
        }

        InitiationDialog();

        if (dialog != null && context != null) {
            dialog.show();
        }
    }
}
