package com.example.renosyahputra.customgalleryfilepicker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.renosyahputra.customgalleryfilepicker.res.adapter.CustomAdapterGalleryFile;
import com.example.renosyahputra.customgalleryfilepicker.res.interfaceClass.ShowDialog;
import com.example.renosyahputra.customgalleryfilepicker.res.obj.GalleryFileObj;
import com.example.renosyahputra.customgalleryfilepicker.res.obj.lang.ActivityGalleryFilePickerLang;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import static com.example.renosyahputra.customgalleryfilepicker.res.DialogRes.GetAllGalleryImage;
import static com.example.renosyahputra.customgalleryfilepicker.res.DialogRes.RequestPermissionReadStorageIfNotGranted;
import static com.example.renosyahputra.customgalleryfilepicker.res.DialogRes.SetEnglishLang;
import static com.example.renosyahputra.customgalleryfilepicker.res.DialogRes.SetIndoLang;

public class CustomDialogGalleryFilePicker {


    private Context context;
    private ShowDialog.OnShowMyFileListener showDialogListener;
    private AlertDialog dialog;
    private int color = 0;
    private ArrayList<GalleryFileObj> fileDatas = new ArrayList<>();
    private ActivityGalleryFilePickerLang lang = new ActivityGalleryFilePickerLang();

    public CustomDialogGalleryFilePicker(@NonNull Context context) {
        this.context = context;
    }

    public void SetOnShowDialog(ShowDialog.OnShowMyFileListener showDialogListener){
        this.showDialogListener = showDialogListener;
    }


    public void SetTheme(int color){
        this.color = color;
    }

    private Boolean SetFull = false;

    public void SetFullScreenActivity(Boolean SetFull){
        this.SetFull = SetFull;
    }

    public void SetIndonesian(){
        SetIndoLang(lang);
    }

    public void SetEnglish(){
        SetEnglishLang(lang);
    }

    private void InitiationDialog(){


        fileDatas.clear();
        for (GalleryFileObj data : GetAllGalleryImage(context)){
            if (data.GetFileExtension().equals(GalleryFileObj.FormatJPG) || data.GetFileExtension().equals(GalleryFileObj.FormatPNG)){
                fileDatas.add(data);
            }
        }

        LayoutInflater inflater = ((Activity)this.context).getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_alert_dialog_file_picker,null);

        dialog = new AlertDialog.Builder(context)
                .setNegativeButton(lang.dialogBatal, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();

        TextView title = v.findViewById(R.id.barTitle);
        title.setText(lang.dialogTitle);

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

        if (lang.title.equals("")){
            SetEnglishLang(lang);
        }

        if (RequestPermissionReadStorageIfNotGranted(context,lang)){
            return;
        }



        InitiationDialog();

        if (dialog != null && context != null) {
            dialog.show();
        }
    }

    public void ShowPickupActivity(){
        if (lang.title.equals("")){
            SetEnglishLang(lang);
        }

        if (RequestPermissionReadStorageIfNotGranted(context,lang)){
            return;
        }

        DialogFragmentToActivity dialogFragmentToActivity = new DialogFragmentToActivity();
        dialogFragmentToActivity.SetOnShowDialog(showDialogListener);
        dialogFragmentToActivity.SetTheme(color);
        dialogFragmentToActivity.SetFullScreen(SetFull);
        dialogFragmentToActivity.show(((Activity)context).getFragmentManager(),"mydialog");
    }

    @SuppressLint("ValidFragment")
    private class DialogFragmentToActivity extends DialogFragment{

        private View v;
        private Context context;

        private int RequestCode = 0;

        private ShowDialog.OnShowMyFileListener showDialogListener;

        public void SetOnShowDialog(ShowDialog.OnShowMyFileListener showDialogListener){
            this.showDialogListener = showDialogListener;
        }

        private int color = 0;

        public void SetTheme(int color){
            this.color = color;
        }
        private Boolean SetFull =false;

        public void SetFullScreen(Boolean SetFull){
            this.SetFull = SetFull;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.empty_fragment_dialog,container,false);
            context = getActivity();

            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

            Random rnd = new Random();
            RequestCode = rnd.nextInt(1000) - 1;

            Intent i = new Intent(context, CustomActivityGalleryFilePicker.class);
            i.putExtra("color",color);
            i.putExtra("full",SetFull);
            i.putExtra("lang",lang);
            startActivityForResult(i, RequestCode);

            return v;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == RequestCode && Activity.RESULT_OK == resultCode && showDialogListener != null && data != null){
                GalleryFileObj obj = (GalleryFileObj) data.getSerializableExtra("data");
                showDialogListener.OnChoosedFile(obj.GetFileName(),new File(obj.Path));
            }
            getDialog().dismiss();
        }
    }
}
