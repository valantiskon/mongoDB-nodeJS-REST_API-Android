package com.example.user.rest_api;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.lang.String;



// public class MainActivity extends Activity implements AsyncResponse
public class MainActivity extends Activity implements Serializable  {

    private List<mongoDB_clients> list;
    private SwipeRefreshLayout refreshLayout;
    private Retrofit retrofit;
    private MongodbCommun mongoservice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/api/clients_app/") // For emulator: 10.0.2.2 | For real device: use your local IP address instead
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mongoservice = retrofit.create(MongodbCommun.class);

        refreshLayout = findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getData();
                    }
                }
        );

        addButtons(list);

        if (savedInstanceState != null)
            Toast.makeText(this, getApplicationContext().getResources().getString(R.string.orient), Toast.LENGTH_SHORT).show();
    }



    private void getData() {
        Call<List<mongoDB_clients>> call = mongoservice.getAllCustomers();
        call.enqueue(new Callback<List<mongoDB_clients>>() {
            @Override
            public void onResponse(@NonNull Call<List<mongoDB_clients>> call, @NonNull Response<List<mongoDB_clients>> response) {
                System.out.println("PASSED");
                if (list != null) {
                    list.clear();
                    list.addAll(Objects.requireNonNull(response.body()));
                } else
                    list = Objects.requireNonNull(response.body());
                addButtons(list);
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<mongoDB_clients>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    public void addButtons(List<mongoDB_clients> client_list) {
        list = client_list;

        if(client_list != null) {
            LinearLayout buttonContainer = (LinearLayout) findViewById(R.id.linearButton);
            buttonContainer.removeAllViews();
            for (mongoDB_clients custID : client_list) {
                String name = custID.getName();
                String lstname = custID.getLastName();
                int age = custID.getAge();

                ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.buttonstyle);
                Button button = new Button(newContext);

                // INSTEAD OF setId, setTag IS USED TO USE STRINGS AS ID (setId ACCEPTS ONLY INTEGERS)
                button.setTag(custID.getId());

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clients(v);
                    }
                });

                String buttn_text = getApplicationContext().getResources().getString(R.string.nam) + name + "\n" + getApplicationContext().getResources().getString(R.string.lastn) + lstname + "\n" + getApplicationContext().getResources().getString(R.string.age) + age;
                button.setText(buttn_text);

                button.setBackgroundColor(Color.parseColor("#5b5a5c"));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 24, 0, 0);
                button.setLayoutParams(params);

                buttonContainer.addView(button);
            }
        }
    }


    public void clients(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
        System.out.println((String) view.getTag());
        String sendID = "";
        for(mongoDB_clients object : list){
            if(object.getId().equals((String) view.getTag())) // getTag returns an object, so it needs to be type casted to String
                sendID = object.getId();
        }
        intent.putExtra("custid", sendID);

        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST",(Serializable) list);
        intent.putExtra("BUNDLE", args);

        startActivity(intent);
    }


    public void addclient(View view) {
        Intent intent = new Intent(this, Main3Activity.class);
        intent.putExtra("addedit", "add");

        startActivity(intent);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
    }


}