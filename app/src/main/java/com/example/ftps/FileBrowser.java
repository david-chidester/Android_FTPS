package com.example.ftps;

import android.content.Context;
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
    private FileBrowserAdapter adapter;
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
        System.out.println("fb client connected = " + ma.getFtpsClient().isConnected());
        //make an array for files
        try {
            FTPFile fileArray[] = ma.ftpsClient.listFiles();
            for (int i = 0; i < fileArray.length; i++) {
                System.out.println(fileArray[i].getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //make an array for directories
        try {
            FTPFile dirArray[] = ma.ftpsClient.listDirectories();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Make String for current directory
        try {
            String currentDir = ma.ftpsClient.printWorkingDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void testRecyclerView(final ArrayList<FileBrowserListItem> browserList) {
        browserList.add(new FileBrowserListItem(R.drawable.ic_folder_black_24dp, "Documents", "63.3 kb", true));
        browserList.add(new FileBrowserListItem(R.drawable.ic_folder_black_24dp, "Downloads", "5.5 gb", true));
        browserList.add(new FileBrowserListItem(R.drawable.ic_folder_black_24dp, "Desktop", "37.3 mb", true));
        browserList.add(new FileBrowserListItem(R.drawable.ic_folder_black_24dp, "Music", "122.9 gb", true));
        browserList.add(new FileBrowserListItem(R.drawable.ic_folder_black_24dp, "Pictures", "19.5 gb", true));
        browserList.add(new FileBrowserListItem(R.drawable.ic_folder_black_24dp, "programs", "1.8 mb", true));
        browserList.add(new FileBrowserListItem(R.drawable.ic_folder_black_24dp, "Public", "0 b", true));
        browserList.add(new FileBrowserListItem(R.drawable.ic_folder_black_24dp, "Videos", "4.3 gb", true));
        browserList.add(new FileBrowserListItem(R.drawable.ic_file_download_black_24dp, "hello.txt", "46 b", false));

        recyclerView = findViewById(R.id.fileRecyclerView);
        // ensures that the recycler view does not change size no matter how many items are in the list
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new FileBrowserAdapter(browserList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnClockListener(new FileBrowserAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                FileBrowser.this.itemDownloadedSnackbar(recyclerView, browserList.get(position));
            }
        });
    }

    void itemDownloadedSnackbar(View v, FileBrowserListItem fbli) {
        Snackbar.make(v, "Downloaded " + fbli.getFileName(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
