package com.example.android.statusdownloaderforwhatsaap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

public class Video extends AppCompatActivity {
    ImageView download,mychatapp,share;
    VideoView mparticularVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        getSupportActionBar().setTitle("Video");
        mparticularVideo = findViewById(R.id.particularvideo);
        download = findViewById(R.id.download);
        share = findViewById(R.id.share);
        mychatapp = findViewById(R.id.chatapp);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Video.this, "Share is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        String destpath = intent.getStringExtra("DEST_PATH_VIDEO");
        String file = intent.getStringExtra("FILE_VIDEO");
        String uri = intent.getStringExtra("URI_VIDEO");
        String filename = intent.getStringExtra("FILENAME_VIDEO");


        File destpathagain = new File(destpath);
        File file1 = new File(file);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mparticularVideo);
       Uri uri1 =Uri.parse(uri);
       mparticularVideo.setMediaController(mediaController);
       mparticularVideo.setVideoURI(uri1);
       mparticularVideo.requestFocus();
       mparticularVideo.start();
//        Glide.with(getApplicationContext()).load(uri).into(mparticularVideo);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    org.apache.commons.io.FileUtils.copyFileToDirectory(file1,destpathagain);
                }
                catch (IOException e){
                    e.printStackTrace();
                }

                MediaScannerConnection.scanFile(getApplicationContext()
                        , new String[]{destpath + filename}
                        , new String[]{"*/*"}, new MediaScannerConnection.MediaScannerConnectionClient() {
                            @Override
                            public void onMediaScannerConnected() {

                            }

                            @Override
                            public void onScanCompleted(String s, Uri uri) {

                            }
                        });

                Dialog dialog = new Dialog(Video.this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.show();

                Button btn = findViewById(R.id.okbtn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        });

    }
}