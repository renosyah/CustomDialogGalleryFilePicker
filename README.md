# Custom Dialog Gallery File Picker

hanya sebuah contoh sederhana mempublikasikan android library ke jitpack yang menampilkan gambar dari gallery dan berikut adalah preview dan instalasi : 

## Preview 

* Tampilan Daftar Gambar dari galery

![GitHub Logo](/image/1.png)





* Tampilan Setelah memilih gambar

![GitHub Logo](/image/2.png)


## Instalasi


* tambahkan kedalam build.gradle : 
```

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

```


* tambahkan kedalam app.gradle : 
```

	implementation 'com.github.renosyah:CustomDialogGalleryFilePicker:v1.2'
	
```

* untuk penggunaan berikut adalah contohnya : 
```

CustomDialogGalleryFilePicker dialog = CustomDialogGalleryFilePicker(this);
	dialog.SetOnShowDialog(new ShowDialog.OnShowMyFileListener() {
            	@Override
            	public void OnChoosedFile(String filename, File file) {

			// tempatkan kode anda disini untuk mengelola file setelah dipilih

            		}
        	});

	dialog.ShowDialog();

```


* dialog juga dapat di sesuaikan warnanya :

```

int CustomColor = ResourcesCompat.getColor(this.getResources(), R.color.colorPrimaryDark,null);
dialog.SetTheme(CustomColor);
dialog.ShowDialog();


```
