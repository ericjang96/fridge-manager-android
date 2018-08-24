package com.example.kevin.fridgemanager.Generators;

import java.util.UUID;

public class FridgeIdGenerator {

    public static String generateID(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
