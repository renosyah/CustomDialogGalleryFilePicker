package com.example.renosyahputra.customgalleryfilepicker.res.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
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
//        if (color != 0) {
//            holder.name.setTextColor(color);
//        }

        switch (item.GetFileExtension()) {
            case GalleryFileObj.FormatJPG:
            case GalleryFileObj.FormatPNG:
                Picasso.get()
                        .load(new File(item.Path))
                        .placeholder(ResourcesCompat.getDrawable(context.getResources(), R.drawable.jpg_file, null))
                        .resize(200, 200)
                        .into(holder.image);

                break;
            case GalleryFileObj.FormatVIDEO_3GP:
            case GalleryFileObj.FormatVIDEO_MP4:
                holder.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.video_file, null));
                if(color != 0) {
                    holder.image.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                }

                break;
            case GalleryFileObj.FormatMP3:
                holder.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.music_file, null));
                if(color != 0) {
                    holder.image.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                }
                break;

            case GalleryFileObj.FormatPDF:
                holder.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.pdf_logo, null));
                if(color != 0) {
                    holder.image.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                }
                break;
            case GalleryFileObj.FormatZIP:
                holder.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.zip_icon, null));
                if(color != 0) {
                    holder.image.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                }
                break;

                default:
                    holder.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.unknown_file_type, null));
                    if(color != 0) {
                        holder.image.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                    }

                    break;
        }

        return row;
    }

    private class DataList {
        public ImageView image;
        public TextView name;
    }
}
