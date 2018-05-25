package com.example.elaraby.baking.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.elaraby.baking.adapters.MainRecyclerAdapter;
import com.example.elaraby.baking.models.Ingredent;
import com.example.elaraby.baking.models.Model;
import com.example.elaraby.baking.models.Steps;
import com.example.elaraby.baking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    public static ArrayList<Model> modelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isNetworkAvailable(this)) {
            recyclerView = findViewById(R.id.main_recycyler);
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
            recyclerView.setHasFixedSize(false);
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("loading");
            progressDialog.setCancelable(false);
            new Asyn().execute();
        }else{
            displayAlert();
        }
    }
    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!= null && networkInfo.isConnected();
    }

    public void displayAlert()
    {
        new AlertDialog.Builder(this).setMessage("Please Check Your Internet Connection and Try Again")
                .setTitle("Network Error")
                .setCancelable(true)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton){
                                finish();
                            }
                        })
                .show();
    }

    class Asyn extends AsyncTask<Void, Void, Void> {
        HttpsURLConnection httpsURLConnection = null;
        BufferedReader bufferedReader;
        InputStream inputStream;
        StringBuffer stringBuffer;
        String json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            modelArrayList = new ArrayList<>();
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String strurl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
            try {
                URL url = new URL(strurl);
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.connect();

                inputStream = httpsURLConnection.getInputStream();
                if (inputStream == null)
                    return null;
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                    stringBuffer.append(line + "\n");
                json = stringBuffer.toString();
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    ArrayList<Ingredent> list1 = new ArrayList<>();
                    ArrayList<Steps> list2 = new ArrayList<>();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONArray ingerd = jsonObject.getJSONArray("ingredients");
                    int id = jsonObject.getInt("id");
                    for (int j = 0; j < ingerd.length(); j++) {
                        JSONObject jsonObject1 = ingerd.getJSONObject(j);
                        list1.add(new Ingredent(jsonObject1.getDouble("quantity"), jsonObject1.getString("measure"), jsonObject1.getString("ingredient")));
                    }
                    JSONArray st = jsonObject.getJSONArray("steps");
                    for (int j = 0; j < st.length(); j++) {
                        JSONObject jsonObject1 = st.getJSONObject(j);
                        list2.add(new Steps(jsonObject1.getInt("id"), jsonObject1.getString("shortDescription"), jsonObject1.getString("description"), jsonObject1.getString("videoURL"), jsonObject1.getString("thumbnailURL")));
                    }
                    modelArrayList.add(new Model(jsonObject.getInt("id"), jsonObject.getString("name"), list1, list2, jsonObject.getInt("servings"), jsonObject.getString("image")));
                }


            } catch (MalformedURLException e) {

            } catch (IOException e) {
            } catch (JSONException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (modelArrayList != null || modelArrayList.size() > 0)
                recyclerView.setAdapter(new MainRecyclerAdapter(modelArrayList, getApplicationContext()));

        }
    }
}
