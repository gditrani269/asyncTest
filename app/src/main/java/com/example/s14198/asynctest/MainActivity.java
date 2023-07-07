package com.example.s14198.asynctest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.os.AsyncTask;

import android.view.View;
import android.widget.Button;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;

import android.widget.TextView;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.InputStreamReader;

//public class MainActivity extends ActionBarActivity {
public class MainActivity extends ActionBarActivity {
    URL ImageUrl = null;
    TextView info;
    EditText serverIP;
    String sRta, sServerIP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.asyncTask);
        Button buttonOn=findViewById(R.id.buttonON);
        Button buttonOff=findViewById(R.id.buttonOFF);
        Button buttonIP=findViewById(R.id.buttonIP);
        info = (TextView) findViewById(R.id.info);
        serverIP = (EditText) findViewById(R.id.serverIP);
        buttonIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sServerIP = serverIP.getText().toString();
                info.setText(sServerIP);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskExample asyncTask=new AsyncTaskExample();
                asyncTask.execute("http://" + sServerIP);
            }
        });
        buttonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskExample asyncTask=new AsyncTaskExample();
                asyncTask.execute("http://" + sServerIP + "/ON");
            }
        });
        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskExample asyncTask=new AsyncTaskExample();
                asyncTask.execute("http://" + sServerIP + "/OFF");
            }
        });
    }
//    private class AsyncTaskExample extends AsyncTask<String, String, Bitmap> {
    private class AsyncTaskExample extends AsyncTask<String, String, String> {
/*        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("Please wait...It is downloading");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }*/
        @Override
//        protected Bitmap doInBackground(String... strings) {
        protected String doInBackground(String... strings) {
            try {
/*                ImageUrl = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                sRta = "conn.getResponseMessage()";*/
                URL url = new URL(strings[0]);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();
                BufferedReader rd =
                        new BufferedReader(
                                new InputStreamReader(connection.getInputStream()));

                StringBuilder content = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    content.append(line).append("\n");
                }
                rd.close();
                connection.disconnect();
                return content.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            return bmImg;
            return sRta;
        }
        @Override
//        protected void onPostExecute(Bitmap bitmap) {
        protected void onPostExecute(String sMsg) {
//            super.onPostExecute(bitmap);
            info.setText("rta sarasa: " + sMsg);
/*            if(imageView!=null) {
                p.hide();
                imageView.setImageBitmap(bitmap);
            }else {
                p.show();
            }*/
        }
    }
}