package com.example.kevin.fridgemanager.Generators;

import java.util.UUID;

/**
 * Created by kevin on Aug 23, 2018
 **/
public class UniqueIdGenerator {

    public static String generateID(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
