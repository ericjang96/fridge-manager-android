package com.example.kevin.fridgemanager.Singletons;

public class GlobalVariables {
    //Debugging or Server URL
//    public static final String connectionURL = "http://10.0.2.2:3000";
//    public static final String connectionAddress = "10.0.2.2";

    public static final String connectionURL = "http://ec2-18-236-130-40.us-west-2.compute.amazonaws.com:3000";
    public static final String connectionAddress = "ec2-18-236-130-40.us-west-2.compute.amazonaws.com";

    public static final int connectionPort = 3000;

    public static final String fridgeViewURL = "/fridges/ingredients/view";
    public static final String fridgeIngredientsUrl = "/fridges/ingredients";

    public static final String userNamesURL = "/users/names";
    public static final String userGroceryListsURL = "/users/groceryLists";

    public static final String groceryListsURL = "/groceryLists";

    public static final String fromActivity = "FROM_ACTIVITY";
    public static final String loginActivity = "Login_Activity";



    public static final String user_id = "user_id";
    public static final String fridge_id = "fridge_id";
    public static final String logged_in = "isLoggedIn";
}
