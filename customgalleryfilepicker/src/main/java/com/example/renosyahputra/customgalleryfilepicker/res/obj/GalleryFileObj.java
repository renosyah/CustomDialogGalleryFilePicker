package com.example.renosyahputra.customgalleryfilepicker.res.obj;

import android.net.Uri;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.Serializable;

public class GalleryFileObj implements Serializable {
    public String FileName = "";
    public String Path = "";

    public GalleryFileObj(String fileName, String path) {
        FileName = fileName;
        Path = path;
    }
    public String GetFileName(){
        File f = new File(Path);
        return f.getName();
    }

    public String GetFileExtension(){
        return  MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(this.Path)).toString());
    }

    public String GetFiletTypeExtension(){
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(Uri.fromFile(new File(this.Path)).toString());
    }

    public static final String FormatJPG = "jpg";
    public static final String FormatPNG = "png";

    public static final String FormatVIDEO_MP4 = "mp4";
    public static final String FormatVIDEO_3GP = "3gp";

    public static final String FormatMP3 = "mp3";

    public static final String FormatPDF = "pdf";

    public static final String FormatZIP = "zip";
}
