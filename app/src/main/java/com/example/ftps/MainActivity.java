package com.example.ftps;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.ftp.FTP;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initializing ftp client object
        final FTPSClient ftpsClient = new FTPSClient();
        FTP ftp = new FTP();

        //declarations for edit text views

        final EditText address = findViewById(R.id.addressEditText);
        final EditText username = findViewById(R.id.usernameEditText);
        final EditText password = findViewById(R.id.passwordEditText);

        Button btn = findViewById(R.id.connectButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //test strings
                String testAdr = "10.10.10.10";
                String testUser = "dchid";
                String testPass = "password";

                //Getting string from editText Views
                String adrString = address.getText().toString();
                String unString = username.getText().toString();
                String passwdString = password.getText().toString();

                if (adrString.equals(testAdr) && unString.equals(testUser) && passwdString.equals(testPass)){
                    //opens file browser activity
                    try {
                        ftpsClient.login(unString, passwdString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(MainActivity.this, FileBrowser.class));
                }
                else {
                    Snackbar.make(v, "Login Failed", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


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
