package com.example.user.bank;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Main3Activity extends AppCompatActivity implements Serializable {

    private String modeID;
    private String clname;
    private String cllastnam;
    private int clage;
    private String custID;
    private List<mongoDB_clients> list;
    private MongodbCommun mongoservice;

    @SuppressWarnings("unchecked") //Used to suppress a warning during list initialization
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main3);

        Bundle intent = getIntent().getExtras();

        assert intent != null;
        modeID = intent.getString("addedit");

        // Get list only if EDIT function is called (no need to pass 'list' when adding new element)
        if (modeID != null) {
            if(modeID.equals("edit")){
                custID = intent.getString("custid");

                Bundle args = intent.getBundle("BUNDLE");
                assert args != null;
                list = (List<mongoDB_clients>) args.getSerializable("ARRAYLIST");

                clname = intent.getString("name");
                cllastnam = intent.getString("last_name");
                clage = Integer.parseInt(intent.getString("age"));

                EditText nm = (EditText) findViewById(R.id.nam);
                EditText lsnm = (EditText) findViewById(R.id.lstnam);
                EditText ag = (EditText) findViewById(R.id.ag);
                nm.setText(clname);

                lsnm.setText(cllastnam);

                String rated = Integer.toString(clage);
                ag.setText(rated);
            }
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:80/api/test_app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mongoservice = retrofit.create(MongodbCommun.class);
    }

    public void save(View view) {
        EditText name = (EditText) findViewById(R.id.nam);
        EditText lstname = (EditText) findViewById(R.id.lstnam);
        EditText age = (EditText) findViewById(R.id.ag);

        if(name.getText().toString().isEmpty() || lstname.getText().toString().isEmpty() || age.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.noentry), Toast.LENGTH_LONG).show();
        }else{
            String nm = name.getText().toString();
            String lstnm = lstname.getText().toString();
            int ag = Integer.parseInt(age.getText().toString());

            if (ag < 1 || lstnm.length() < 1 || nm.length() < 1) {
                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.err), Toast.LENGTH_LONG).show();
            } else {
                if (modeID.equals("edit")) {
                    mongoDB_clients c = new mongoDB_clients(name.getText().toString() + lstname.getText().toString() + age.getText().toString(), name.getText().toString(), lstname.getText().toString(), Integer.parseInt(age.getText().toString()));
                    System.out.println(name.getText().toString() + lstname.getText().toString() + age.getText().toString());
                    Call<mongoDB_clients> call = mongoservice.updateCustomer(custID, c);
                    System.out.println(custID);

                    call.enqueue(new Callback<mongoDB_clients>() {
                        @Override
 // 'list' does not get back to main_activity updated, so to update the list off main_acivity needs to use update function from server and store the values
                        public void onResponse(@NonNull Call<mongoDB_clients> call, @NonNull Response<mongoDB_clients> response) {
                            Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(@NonNull Call<mongoDB_clients> call, @NonNull Throwable t) {
                            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    finish();

                } else if (modeID.equals("add")) {
                    JSONObject postData = new JSONObject();
                    try {
                        postData.put("id", name.getText().toString() + lstname.getText().toString() + age.getText().toString());
                        postData.put("name", name.getText().toString());
                        postData.put("last_name", lstname.getText().toString());
                        postData.put("age", Integer.parseInt(age.getText().toString()));

                        String json = postData.toString();
                        System.out.println(json);
                        new SendDeviceDetails().execute("http://10.0.2.2:80/api/test_app/", "ADD", json);

                        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    finish();
                }
            }
        }
    }

    public void cancel(View view) {
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

}