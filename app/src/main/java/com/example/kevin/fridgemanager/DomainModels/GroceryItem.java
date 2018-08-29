package com.example.kevin.fridgemanager.DomainModels;

/**
 * Created by kevin on Aug 28, 2018
 **/
public class GroceryItem {
    private String name;
    private int amount;
    private String comment;

    public GroceryItem(String name, int amount, String comment){
        this.name = name;
        this.amount = amount;
        this.comment = comment;
    }

    public GroceryItem(String name, int amount){
        this.name = name;
        this.amount = amount;
        this.comment = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
