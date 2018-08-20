package com.example.renosyahputra.customgalleryfilepicker.res.interfaceClass;

import android.support.annotation.NonNull;

import java.io.File;

public class ShowDialog {
    public interface OnShowMyFileListener {
       void OnChoosedFile(@NonNull String fileName, @NonNull File file);
    }
}
