# Custom Dialog Gallery File Picker

hanya sebuah contoh sederhana mempublikasikan android library ke jitpack yang menampilkan gambar dari gallery dan berikut adalah preview dan instalasi : 

## Preview 


* Tampilan Dialog untuk Daftar Gambar dari galery

![GitHub Logo](/image/1.png)




* Tampilan Activity untuk Daftar Gambar dari galery

![GitHub Logo](/image/5.png)





* Tampilan Setelah memilih gambar

![GitHub Logo](/image/6.png)


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

	dependencies {
		...
		implementation 'com.github.renosyah:CustomDialogGalleryFilePicker:v1.5'
	
	}

```

* untuk penggunaan berikut adalah contohnya : 

```

CustomDialogGalleryFilePicker dialog = CustomDialogGalleryFilePicker(this);
	dialog.SetFullScreenActivity(true);
        dialog.SetIndonesian();
	dialog.SetOnShowDialog(new ShowDialog.OnShowMyFileListener() {
            	@Override
            	public void OnChoosedFile(String filename, File file) {

			// tempatkan kode anda disini untuk mengelola file setelah dipilih

            		}
        	});

	dialog.ShowPickupActivity();

```

* pengaturan untuk bahasa indonesia/inggris dan jendela activity :

```

	dialog.SetFullScreenActivity(true);
        dialog.SetIndonesian();


```


* dialog dan activity dapat di sesuaikan warnanya :

```

	int CustomColor = ResourcesCompat.getColor(this.getResources(), R.color.colorPrimaryDark,null);
	dialog.SetTheme(CustomColor);
	dialog.ShowPickupActivity();


```


* dialog atau activity dapat dipilih sesuai kebutuhan : 
```

	dialog.ShowDialog(); // untuk dialog namun hanya mengakses gambar

	dialog.ShowPickupActivity();  // untuk activity dapat mengakses gambar,musik,video dan lainya


```