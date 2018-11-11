package com.example.user.rest_api;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {

    private String custID;
    private List<mongoDB_clients> list;
    private Retrofit retrofit;
    private MongodbCommun mongoservice;

    @SuppressWarnings("unchecked") //Used to suppress a warning during list initialization
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        custID = extras.getString("custid");

        Bundle args = extras.getBundle("BUNDLE");
        assert args != null;
        list = (List<mongoDB_clients>) args.getSerializable("ARRAYLIST");

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/api/clients_app/") // For emulator: 10.0.2.2 | For real device: use your local IP address instead
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mongoservice = retrofit.create(MongodbCommun.class);

        String name = "test";
        String lstname = "test";
        int age = 0;
        for(mongoDB_clients object : list){
            if(object.getId().equals(custID)){
                name = object.getName();
                lstname = object.getLastName();
                age = object.getAge();
            }
        }

        Button client_name = (Button) findViewById(R.id.name1);
        Button client_lstname = (Button) findViewById(R.id.lastname1);
        Button client_age = (Button) findViewById(R.id.age1);

        client_name.setText(name);
        client_lstname.setText(lstname);

        String ag = Integer.toString(age);
        client_age.setText(ag);
    }

    public void addedit(View view) {
        Intent intent = new Intent(this, Main3Activity.class);
        intent.putExtra("addedit", "edit");

        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST",(Serializable) list);
        intent.putExtra("BUNDLE", args);

        intent.putExtra("custid", custID);

        Button nam = (Button) findViewById(R.id.name1);
        Button lstnam = (Button) findViewById(R.id.lastname1);
        Button ag = (Button) findViewById(R.id.age1);
        String clientname = nam.getText().toString();
        String clientlastname = lstnam.getText().toString();
        String clientage = ag.getText().toString();
        intent.putExtra("name", clientname);
        intent.putExtra("last_name", clientlastname);
        intent.putExtra("age", clientage);

        startActivity(intent);
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();

        for(mongoDB_clients object : list){
            if(object.getId().equals(custID)){
                Call<mongoDB_clients> call = mongoservice.getCustomer(custID);
                call.enqueue(new Callback<mongoDB_clients>() {
                    @Override
                    public void onResponse(@NonNull Call<mongoDB_clients> call, @NonNull Response<mongoDB_clients> response) {
                        assert response.body() != null;
                        String name = Objects.requireNonNull(response.body().getName());
                        assert response.body() != null;
                        String lstname = Objects.requireNonNull(response.body().getLastName());
                        assert response.body() != null;
                        int age = Objects.requireNonNull(response.body().getAge());

                        Button client_name = (Button) findViewById(R.id.name1);
                        Button client_lstname = (Button) findViewById(R.id.lastname1);
                        Button client_age = (Button) findViewById(R.id.age1);

                        client_name.setText(name);
                        client_lstname.setText(lstname);

                        String ag = Integer.toString(age);
                        client_age.setText(ag);
                    }

                    @Override
                    public void onFailure(@NonNull Call<mongoDB_clients> call, @NonNull Throwable t) {
                        Toast.makeText(Main2Activity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }



    public void delclient(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getApplicationContext().getResources().getString(R.string.delentry));
        alert.setMessage(getApplicationContext().getResources().getString(R.string.delquest));
        alert.setPositiveButton(getApplicationContext().getResources().getString(R.string.conf), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pos = -1;
                for(mongoDB_clients object : list){
                    if(object.getId().equals(custID)){
                        pos = list.indexOf(object); // get the index of above customer from the list
                    }
                }
                final int position = pos; // The position of the client in the list, used to list client from the list (not needed though)

                // It is possible to delete a client using a given ID instead of using the ID that mongoDB generates, BUT customer.model.js needs to be changed, in the delete function in the query the _ of _id needs to be deleted
                // String id = list.get(pos).getName() + list.get(pos).getLastName() + list.get(pos).getAge();
                // Call<Void> call = mongoservice.deleteCustomer(id);

                Call<Void> call = mongoservice.deleteCustomer(custID);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        Toast.makeText(getApplicationContext(), "Customer deleted successfully", Toast.LENGTH_SHORT).show();
                        list.remove(position);
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }

                });
                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.sucdel), Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                finish();
            }
        });
        alert.setNegativeButton(getApplicationContext().getResources().getString(R.string.no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }


    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

}