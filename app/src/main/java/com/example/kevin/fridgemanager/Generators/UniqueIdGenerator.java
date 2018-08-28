package com.example.kevin.fridgemanager.Generators;

import java.util.UUID;

public class UniqueIdGenerator {

    public static String generateID(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
