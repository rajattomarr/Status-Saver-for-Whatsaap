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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

public class Picturee extends AppCompatActivity {
    ImageView mparticularimage,download,mychatapp,share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picturee);
        getSupportActionBar().setTitle("Picture");
        mparticularimage = findViewById(R.id.particularimage);
        download = findViewById(R.id.download);
        share = findViewById(R.id.share);
        mychatapp = findViewById(R.id.chatapp);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Picturee.this, "Share is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        String destpath = intent.getStringExtra("DEST_PATH");
        String file = intent.getStringExtra("FILE");
        String uri = intent.getStringExtra("URI");
        String filename = intent.getStringExtra("FILENAME");


        File destpathagain = new File(destpath);
        File file1 = new File(file);

        Glide.with(getApplicationContext()).load(uri).into(mparticularimage);

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

                Dialog dialog = new Dialog(Picturee.this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.show();

                Button  btn = findViewById(R.id.okbtn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        });

    }
}