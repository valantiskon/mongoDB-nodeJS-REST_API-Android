package com.example.user.rest_api;

import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface MongodbCommun {
    // Not used, implemented in SendDeviceDetails with AsyncTask
    @POST("/api/clients_app")
    Call<ResponseBody> createUser(@Body mongoDB_clients c);

    @GET("/api/clients_app")
    Call<List<mongoDB_clients>> getAllCustomers();

    @GET("/api/clients_app/{id}")
    Call<mongoDB_clients> getCustomer(@Path("id") String id);

    @DELETE("/api/clients_app/{id}")
    Call<Void> deleteCustomer(@Path("id") String id);

    @PUT("/api/clients_app/{id}")
    Call<mongoDB_clients> updateCustomer(@Path("id")  String id, @Body mongoDB_clients c);
}