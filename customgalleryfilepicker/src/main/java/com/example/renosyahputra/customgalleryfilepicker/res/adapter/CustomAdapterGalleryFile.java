package com.example.renosyahputra.customgalleryfilepicker.res.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.renosyahputra.customgalleryfilepicker.R;
import com.example.renosyahputra.customgalleryfilepicker.res.obj.GalleryFileObj;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class CustomAdapterGalleryFile extends ArrayAdapter<GalleryFileObj> {

    private Context context;
    private int resource;
    private List<GalleryFileObj> objects = null;

    public CustomAdapterGalleryFile(@NonNull Context context, int resource, @NonNull List<GalleryFileObj> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    int color = 0;
    public void SetColor(int color){
        this.color = color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        DataList holder = null;
        if (row == null){
            LayoutInflater inflater = ((Activity)this.context).getLayoutInflater();
            row = inflater.inflate(resource,parent,false);
            holder = new DataList();

            holder.image = row.findViewById(R.id.FileImageAdapter);
            holder.name = row.findViewById(R.id.FileNameAdapter);

            row.setTag(holder);
        }else{
            holder = (DataList) row.getTag();
        }
        GalleryFileObj item = getItem(position);

        holder.name.setText(item.FileName);
        if (color != 0) {
            holder.name.setTextColor(color);
        }

        Picasso.get()
                .load(new File(item.Path))
                .resize(200,200)
                .into(holder.image);

        return row;
    }

    private class DataList {
        public ImageView image;
        public TextView name;
    }
}
