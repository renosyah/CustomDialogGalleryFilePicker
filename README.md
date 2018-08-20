# Custom Dialog Gallery File Picker

hanya sebuah contoh sederhana membuat alert dialog yang menampilkan gambar dari gallery
berikut adalah preview cara instalasi : 

## Preview 

* Tampilan Daftar Gambar dari galery

![GitHub Logo](/image/1.png)





* Tampilan Setelah memilih gambar

![GitHub Logo](/image/2.png)


## Instalasi


* tambahakan kedalam build.gradle : 
```

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

```


* tambahakan kedalam app.gradle : 
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

int CustomColor = ResourcesCompat.getColor(context.resources, R.color.colorPrimaryDark,null);
dialog.SetTheme(CustomColor);
dialog.ShowDialog();


```
