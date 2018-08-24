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

}
