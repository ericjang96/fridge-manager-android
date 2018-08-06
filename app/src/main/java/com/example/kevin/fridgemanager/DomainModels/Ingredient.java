package com.example.kevin.fridgemanager.DomainModels;

import java.util.Date;

public class Ingredient {
    private String name;
    private String boughtDate;
    private String expiryDate;
    private String amountUnit;
    private Integer amount;

    public Ingredient(String name, String amtUnit, Integer amt){
        this.name = name;
        amountUnit = amtUnit;
        amount = amt;
    }

    public Ingredient(String name, String bought, String expire, String amtUnit, Integer amt){
        this.name = name;
        boughtDate = bought;
        expiryDate = expire;
        amountUnit = amtUnit;
        amount = amt;
    }

    public String getName(){
        return name;
    }

    public String getBoughtDate(){
        return boughtDate;
    }

    public String getExpiryDate(){
        return expiryDate;
    }

    public String getUnit(){
        return amountUnit;
    }

    public int getAmount(){
        return amount;
    }

    public String getAmountString() {
        return amount.toString();
    }


    public void setName(String newName){
        this.name = newName;
    }

    public void setAmount(int newAmt){
        this.amount = newAmt;
    }

    public void setAmountUnit(String newUnit){
        this.amountUnit = newUnit;
    }
}