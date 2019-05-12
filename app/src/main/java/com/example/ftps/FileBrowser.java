package com.example.ftps;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPCmd;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.IOException;
import java.util.ArrayList;

public class FileBrowser extends AppCompatActivity {

    private RecyclerView recyclerView;
    //loads arraylist items into recycler view
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //ArrayList of strings for file data
        ArrayList<String> fileNamesList = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<FileBrowserListItem> testList = new ArrayList<>();
        testRecyclerView(testList);

        //passing ftps instance from Main Activity
        MainActivity ma = new MainActivity();
        FTPSClient ftpsClient = ma.ftpsClient;

        //make an array for files
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

    void testRecyclerView(ArrayList<FileBrowserListItem> browserList) {
        browserList.add(new FileBrowserListItem(R.drawable.ic_folder_black_24dp, "Documents", "5.5 gb", true));
        browserList.add(new FileBrowserListItem(R.drawable.ic_folder_black_24dp, "Downloads", "5.5 gb", true));
        browserList.add(new FileBrowserListItem(R.drawable.ic_folder_black_24dp, "Desktop", "5.5 gb", true));
        browserList.add(new FileBrowserListItem(R.drawable.ic_folder_black_24dp, "Desktop", "5.5 gb", true));
        browserList.add(new FileBrowserListItem(R.drawable.ic_folder_black_24dp, "Music", "122.9 gb", true));

        recyclerView = findViewById(R.id.fileRecyclerView);
        // ensures that the recycler view does not change size no matter how many items are in the list
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new FileBrowserAdapter(browserList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
