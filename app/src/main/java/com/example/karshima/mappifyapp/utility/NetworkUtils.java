package com.example.karshima.mappifyapp.utility;

import android.os.StrictMode;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Capternal on 03/02/16.
 */
public class NetworkUtils {

    public static String getData(String strURL) {
        String strResultFromJson = "";
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            int TIMEOUT_MILLISEC = 60000; // = 10 seconds
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,
                    TIMEOUT_MILLISEC);
            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
            HttpClient objHttpClient = new DefaultHttpClient(httpParams);
            HttpGet objHttpGet = new HttpGet(strURL
            );
            HttpResponse objHttpResponse = objHttpClient.execute(objHttpGet);

            System.out.println("Status Code " + objHttpResponse.getStatusLine());

            InputStream objInputStream = objHttpResponse.getEntity().getContent();


            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    objInputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            objInputStream.close();

            strResultFromJson = sb.toString();
            System.out.println("Final Error Response Body Non==>" + strResultFromJson);
            System.out.println("Final Response From Server = " + strResultFromJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResultFromJson;
    }
}
