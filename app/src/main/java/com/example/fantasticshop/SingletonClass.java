package com.example.fantasticshop;

import com.example.fantasticshop.fragments.HorizontalItems;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SingletonClass {
    private static SingletonClass myInstance = null;
    private  List<HorizontalItems> itemList;
    private final DatabaseReference reference;


    private SingletonClass() {
        itemList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fantasticshop-9cd2b-default-rtdb.firebaseio.com/");
        reference = database.getReference("items");
    }

    public List<HorizontalItems> getItemList() {
        return itemList;
    }

    public DatabaseReference getReference() {
        return reference;
    }



    public static synchronized SingletonClass  getMyInstance() {
        if(myInstance == null){
            myInstance = new SingletonClass();
        }
        return myInstance;
    }
}
