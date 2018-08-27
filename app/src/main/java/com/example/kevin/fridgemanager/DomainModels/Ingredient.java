/*
Created by Kevin Kwon on August 02 2018
 */
package com.example.kevin.fridgemanager.DomainModels;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Ingredient implements Serializable {
    private String name;
    private Date boughtDate;
    private Date expiryDate;
    private String amountUnit;
    private Integer amount;
    private Integer position;

    // if Date is not specified, say insert = bought date
    public Ingredient(String name, Integer amt, String amtUnit){

        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        // expiry date is 2 weeks later (for now)
        c.add(Calendar.DATE, 14);
        Date expiryDate = c.getTime();

        boughtDate = currentDate;
        this.expiryDate = expiryDate;
        this.name = name;
        amountUnit = amtUnit;
        amount = amt;
    }

    public Ingredient(String name, Date bought, Date expire, String amtUnit, Integer amt){
        this.name = name;
        boughtDate = bought;
        expiryDate = expire;
        amountUnit = amtUnit;
        amount = amt;
    }

    public String getName(){
        return name;
    }

    public Date getBoughtDate(){
        return boughtDate;
    }

    public Date getExpiryDate(){
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

    public int getPosition(){
        return position;
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

    public void setPosition(int position){
        this.position = position;
    }
}