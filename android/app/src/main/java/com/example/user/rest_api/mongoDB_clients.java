package com.example.user.rest_api;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class mongoDB_clients implements Serializable {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("age")
    private int age;


    public mongoDB_clients(String id, String name, String last_name, int age){
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.age = age;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getLastName() {
        return last_name;
    }

    @Override
    public String toString() {
        return "mongoDB_clients{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
