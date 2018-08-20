package com.example.renosyahputra.customgalleryfilepicker.res.obj;

import java.io.File;

public class GalleryFileObj {
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
}
