package com.example.ftps;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPCmd;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.IOException;

public class FileBrowser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //make an array for files
        FTPSClient ftpsClient = new FTPSClient();

        try {
            FTPFile fileArray[] = ftpsClient.listFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //make an array for directories
        try {
            FTPFile dirArray[] = ftpsClient.listDirectories();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Make String for current directory
        try {
            String currentDir = ftpsClient.printWorkingDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
