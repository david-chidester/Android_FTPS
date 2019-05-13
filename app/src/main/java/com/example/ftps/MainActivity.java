package com.example.ftps;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import org.apache.commons.net.ftp.FTPCmd;
import org.apache.commons.net.ftp.FTPCommand;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.ftp.FTP;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.apache.commons.net.ftp.FTPCommand.RETRIEVE;

public class MainActivity extends AppCompatActivity {

    //ftp ports
    int dataPort = 20;
    int controlPort = 21;

    //initializing ftp client object
    public final FTPSClient ftpsClient = new FTPSClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setting network policy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //declarations for edit text views

        final EditText address = findViewById(R.id.addressEditText);
        final EditText username = findViewById(R.id.usernameEditText);
        final EditText password = findViewById(R.id.passwordEditText);

        //open about this app activity
        Button about = findViewById(R.id.aboutButton);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutApp.class));
            }
        });

        Button btn = findViewById(R.id.connectButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting string from editText Views
                String adrString = address.getText().toString();
                String unString = username.getText().toString();
                String passwdString = password.getText().toString();

                try {
                    InetAddress ipv4 = Inet4Address.getByName(adrString);
                    System.out.println(ipv4.toString());
                    ftpsClient.connect(ipv4, controlPort);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (ftpsClient.isConnected()){
                    System.out.println("CLIENT CONNECTED = " + ftpsClient.isConnected());
                    //opens file browser activity
                    try {
                        ftpsClient.login(unString, passwdString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        ftpsClient.retrieveFile("hello.txt", new FileOutputStream("hello.txt"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MainActivity.this.openFileBrowserActivity(v);
                }
                else {
                    MainActivity.this.loginFailedSnackbar(v);
                }
            }
        });

    }

    //getters for login info
    public String getIPAddress() {
        EditText address = findViewById(R.id.addressEditText);
        return address.getText().toString();
    }

    public String getUserName() {
        EditText username = findViewById(R.id.usernameEditText);
        return username.getText().toString();
    }

    public String getPassWord() {
        EditText username = findViewById(R.id.usernameEditText);
        return username.getText().toString();
    }

    public FTPSClient getFtpsClient() {
        return ftpsClient;
    }

    //method to open file browser activity
    void openFileBrowserActivity(View v) {
        startActivity(new Intent(MainActivity.this, FileBrowser.class));

        Snackbar.make(v, "connection successful", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    void loginFailedSnackbar(View v) {
        Snackbar.make(v, "login failed", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
