package com.example.renosyahputra.customgalleryfilepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.renosyahputra.customgalleryfilepicker.res.adapter.CustomAdapterGalleryFile;
import com.example.renosyahputra.customgalleryfilepicker.res.obj.GalleryFileObj;
import com.example.renosyahputra.customgalleryfilepicker.res.obj.lang.ActivityGalleryFilePickerLang;

import java.util.ArrayList;

import static com.example.renosyahputra.customgalleryfilepicker.res.DialogRes.GetAllGalleryAudio;
import static com.example.renosyahputra.customgalleryfilepicker.res.DialogRes.GetAllGalleryFile;
import static com.example.renosyahputra.customgalleryfilepicker.res.DialogRes.GetAllGalleryVideo;

public class CustomActivityGalleryFilePicker extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemClickListener,SearchView.OnQueryTextListener
        ,SwipeRefreshLayout.OnRefreshListener{

    private Context context;
    private Intent intent;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private int color = 0;
    private Boolean SetFullScreen = false;

    private ArrayList<GalleryFileObj> fileDatas = new ArrayList<>();
    private ArrayList<GalleryFileObj> fileDatas_Cari = new ArrayList<>();

    private GridView GalleryFileGridview;

    private LinearLayout nav_linear_layout;
    private TextView nav_title;

    private TextView DataKosongTextView;

    private SwipeRefreshLayout refresMainGridview;

    private ActivityGalleryFilePickerLang lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_gallery_file_picker);
        InitiationWidget();
    }

    private void InitiationWidget() {

        context = this;
        intent = getIntent();

        color = intent.getIntExtra("color",0);
        SetFullScreen = intent.getBooleanExtra("full",false);
        lang = (ActivityGalleryFilePickerLang) intent.getSerializableExtra("lang");

        if (SetFullScreen){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(lang.title);
        if (color != 0){
            toolbar.setBackgroundColor(color);
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        GalleryFileGridview = (GridView) findViewById(R.id.ActivityGridViewGalleryFile);
        GalleryFileGridview.setOnItemClickListener(this);

        nav_linear_layout = navigationView.getHeaderView(0).findViewById(R.id.nav_linear_layout);
        if (color != 0){
            nav_linear_layout.setBackgroundColor(color);
        }

        nav_title = navigationView.getHeaderView(0).findViewById(R.id.nav_title);
        nav_title.setText(lang.TitleFileType);

        DataKosongTextView = findViewById(R.id.DataKosongTextView);
        DataKosongTextView.setText(lang.fileEmpty);

        if (color != 0){
            DataKosongTextView.setTextColor(color);
        }

        refresMainGridview = findViewById(R.id.refresMainGridview);
        refresMainGridview.setOnRefreshListener(this);

        DefaultSetting();
        SetSlideMenuLang();
    }

    private void DefaultSetting(){
        fileDatas.clear();
        for (GalleryFileObj data : GetAllGalleryFile(context)){
            if (data.GetFileExtension().equals(GalleryFileObj.FormatPNG) || data.GetFileExtension().equals(GalleryFileObj.FormatJPG)){
                fileDatas.add(data);
            }
        }

        SetAdapter(fileDatas);
        getSupportActionBar().setTitle(lang.title + " " +lang.titleSelectImage);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void SetSlideMenuLang(){
        MenuItem imageMenu = navigationView.getMenu().findItem(R.id.nav_image_file);
        MenuItem musicMenu = navigationView.getMenu().findItem(R.id.nav_music_file);
        MenuItem videoMenu =  navigationView.getMenu().findItem(R.id.nav_video_file);
        MenuItem pdfMenu =  navigationView.getMenu().findItem(R.id.nav_document_file);
        MenuItem ZipMenu =  navigationView.getMenu().findItem(R.id.nav_arsip_file);
        MenuItem otherMenu =  navigationView.getMenu().findItem(R.id.nav_unknown_file);

        imageMenu.setTitle(lang.titleSelectImage);
        musicMenu.setTitle(lang.titleSelectMusic);
        videoMenu.setTitle(lang.titleSelectVideo);
        pdfMenu.setTitle(lang.titleSelectPDF);
        ZipMenu.setTitle(lang.titleSelectZip);
        otherMenu.setTitle(lang.titleSelectOther);
    }


    private void SetAdapter(ArrayList<GalleryFileObj> data){

        CustomAdapterGalleryFile adapter = new CustomAdapterGalleryFile(context,R.layout.custom_adapter_gallery_file,data);

        if (color != 0){
            adapter.SetColor(color);
        }
        GalleryFileGridview.setAdapter(adapter);
        if (!(data.size() > 0)){

            refresMainGridview.setVisibility(View.GONE);
            DataKosongTextView.setVisibility(View.VISIBLE);

        }else {

            refresMainGridview.setVisibility(View.VISIBLE);
            DataKosongTextView.setVisibility(View.GONE);

        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        fileDatas.clear();
        fileDatas_Cari.clear();

        if (id == R.id.nav_image_file) {

            for (GalleryFileObj data : GetAllGalleryFile(context)){
                if (data.GetFileExtension().equals(GalleryFileObj.FormatJPG) || data.GetFileExtension().equals(GalleryFileObj.FormatPNG)){
                    fileDatas.add(data);
                }
            }
            getSupportActionBar().setTitle(lang.title + " " +lang.titleSelectImage);

        } else if (id == R.id.nav_music_file){

            for (GalleryFileObj data : GetAllGalleryAudio(context)){
                if (data.GetFileExtension().equals(GalleryFileObj.FormatMP3)){
                    fileDatas.add(data);
                }
            }
            getSupportActionBar().setTitle(lang.title + " " +lang.titleSelectMusic);

        } else if (id == R.id.nav_video_file){
            for (GalleryFileObj data : GetAllGalleryVideo(context)){
                if (data.GetFileExtension().equals(GalleryFileObj.FormatVIDEO_3GP) || data.GetFileExtension().equals(GalleryFileObj.FormatVIDEO_MP4)){
                    fileDatas.add(data);
                }
            }

            getSupportActionBar().setTitle(lang.title + " " +lang.titleSelectVideo);

        } else if (id == R.id.nav_document_file) {


            for (GalleryFileObj data : GetAllGalleryFile(context)){
                if (data.GetFileExtension().equals(GalleryFileObj.FormatPDF)){
                    fileDatas.add(data);
                }
            }

            getSupportActionBar().setTitle(lang.title + " " +lang.titleSelectPDF);

        }else if (id == R.id.nav_arsip_file) {

            for (GalleryFileObj data : GetAllGalleryFile(context)){
                if (data.GetFileExtension().equals(GalleryFileObj.FormatZIP)){
                    fileDatas.add(data);
                }
            }

            getSupportActionBar().setTitle(lang.title + " " +lang.titleSelectZip);

        }else if (id == R.id.nav_unknown_file){

            for (GalleryFileObj data : GetAllGalleryFile(context)){
                if (!data.GetFileExtension().equals(GalleryFileObj.FormatZIP)
                        && !data.GetFileExtension().equals(GalleryFileObj.FormatPDF)
                        && !data.GetFileExtension().equals(GalleryFileObj.FormatVIDEO_3GP)
                        && !data.GetFileExtension().equals(GalleryFileObj.FormatVIDEO_MP4)
                        && !data.GetFileExtension().equals(GalleryFileObj.FormatMP3)
                        && !data.GetFileExtension().equals(GalleryFileObj.FormatJPG)
                        && !data.GetFileExtension().equals(GalleryFileObj.FormatPNG)
                        ) {
                    fileDatas.add(data);
                }

                getSupportActionBar().setTitle(lang.titleSelectOther);

            }
        }

        SetAdapter(fileDatas);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent returnIntent = new Intent();

        if (fileDatas_Cari.size() > 0){

            returnIntent.putExtra("data",fileDatas_Cari.get(i));

        }else {

            returnIntent.putExtra("data",fileDatas.get(i));

        }

        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private SearchView cari;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_activity_gallery_file_picker, menu);
        cari = (SearchView) menu.findItem(R.id.search_file).getActionView();
        cari.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.search_file) {

            cari.setFocusable(true);
            cari.setIconified(false);
            cari.requestFocusFromTouch();

            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        fileDatas_Cari.clear();
        DataKosongTextView.setText(lang.fileNotFound);

        for (GalleryFileObj data : fileDatas){
            if (data.FileName.matches("(?i).*" + s + "(.*)")){
                fileDatas_Cari.add(data);
            }
        }

        SetAdapter(fileDatas_Cari);
        return false;
    }

    @Override
    public void onRefresh() {
        fileDatas_Cari.clear();
        SetAdapter(fileDatas);
        refresMainGridview.setRefreshing(!refresMainGridview.isRefreshing());
    }
}
