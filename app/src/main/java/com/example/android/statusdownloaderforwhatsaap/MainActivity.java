package com.example.android.statusdownloaderforwhatsaap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {

    int requestcode = 1;
    Adapter adapter;
    File[] files;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    ArrayList<ModelClass> fileslist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        refreshLayout = findViewById(R.id.swipe);
        checkPermission();


//        setuplayout();


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                setuplayout();
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(false);

                        }
                    }, 1000);
                }
            }
        });
    }

    private void setuplayout() {
        fileslist.clear();
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);


        adapter = new Adapter(MainActivity.this, getdata());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    private ArrayList<ModelClass> getdata() {

        ModelClass f;

        String targetpath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + Constant.FOLDER_NAME + "Media/.Statuses";

        File targetdir = new File(targetpath);

        files = targetdir.listFiles();
        for (int i = 0; i < files.length; i++) {

            File file = files[i];
            f = new ModelClass();
            f.setUri(Uri.fromFile(file));
            f.setPath(files[i].getAbsolutePath());
            f.setFilename(file.getName());

            if (!f.getUri().toString().endsWith(".nomedia")) {
                fileslist.add(f);
            }

        }
        return fileslist;
    }


        private void checkPermission () {

            if (SDK_INT > 23) {

                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {
                    Log.e("hello","hhfbfg");
                 setuplayout();

                } else {
                    ActivityCompat.requestPermissions
                            (MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    requestcode);
                }

            } else {
                Toast.makeText(this, "Already Checked", Toast.LENGTH_SHORT).show();
                Log.e("hello","TOAST");
                setuplayout();

            }


        }
    }

