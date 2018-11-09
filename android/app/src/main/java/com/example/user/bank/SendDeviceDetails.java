package com.example.user.bank;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SendDeviceDetails extends AsyncTask<String, List<mongoDB_clients>, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String data = "";
        OutputStream out = null;

        HttpURLConnection httpURLConnection = null;

        if (params[1].equals("ADD")) {

            try {
                URL url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Accept", "application/json");

                httpURLConnection.setRequestProperty("Key", "Value");


                out = new BufferedOutputStream(httpURLConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

                writer.write(params[2]);
                writer.flush();
                writer.close();
                out.close();

                httpURLConnection.connect();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    System.out.println(current);
                    inputStreamData = inputStreamReader.read();
                    data += current;
                    System.out.println(data);

                }
                in.close();
                inputStreamReader.close();

                int responseCode = httpURLConnection.getResponseCode();
                System.out.println("Error if response 200 != " + responseCode);

                if (responseCode == 200)
                    httpURLConnection.disconnect();


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

        }

        return params[1];

    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    protected void onProgressUpdate(String... progress) {
        Log.d("ANDRO_ASYNC", progress[0]);
    }


    @Override
    protected void onPostExecute(String method) {

    }

}