package com.example.kevin.fridgemanager.DomainModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on Aug 27, 2018
 **/
public class GroceryList {
    private String name;
    private String grocery_list_id;
    private List<GroceryItem> groceryItems;

    public GroceryList(String name, String id){
        this.name = name;
        this.grocery_list_id = id;
        this.groceryItems = new ArrayList<>();
    }

    public GroceryList(String name, String id, List<GroceryItem> items){
        this.name = name;
        this.grocery_list_id = id;
        this.groceryItems = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroceryListId() {
        return grocery_list_id;
    }

    public void setGroceryListId(String grocery_id) {
        this.grocery_list_id = grocery_id;
    }

    public List<GroceryItem> getGroceryItems() {
        return groceryItems;
    }

    public void setGroceryItems(List<GroceryItem> groceryItems) {
        this.groceryItems = groceryItems;
    }

    public void addItem(GroceryItem item){
        groceryItems.add(item);
    }
}
