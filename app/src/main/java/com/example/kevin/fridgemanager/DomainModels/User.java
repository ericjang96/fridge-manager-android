package com.example.kevin.fridgemanager.DomainModels;

public class User {
    private String user_id;
    private String password;
    private String fridge_id;
    private String email;

    public User(String user_id, String password, String fridge_id, String email){
        this.user_id = user_id;
        this.password = password;
        this.fridge_id = fridge_id;
        this.email = email;
    }

    public User(String user_id, String fridge_id){
        this.user_id = user_id;
        this.fridge_id = fridge_id;
        this.password = "password stub";
        this.email = "email stub";
    }


    public String getUserId(){
        return user_id;
    }

    public String getPassword(){
        return password;
    }

    public String getFridgeId(){
        return fridge_id;
    }

    public String getEmail(){
        return email;
    }


    public void setUserId(String user_id){
        this.user_id = user_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFridge_id(String fridge_id) {
        this.fridge_id = fridge_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
