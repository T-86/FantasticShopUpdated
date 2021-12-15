package com.example.fantasticshop;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.fantasticshop.fragments.AddItemFragment;
import com.example.fantasticshop.fragments.HomeFragment;
import com.example.fantasticshop.fragments.HorizontalItems;

import java.util.List;


public class HomeActivity extends AppCompatActivity implements CallBackInterface{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        fragmentInjection();

        //Let's load the itemRepository
        ItemRepository repo = new ItemRepository();

//         Let's now update it
        repo.updateData();

        repo.setCallBackInterface(this);


    }

    private void fragmentInjection() {
        Log.i("CALLBACK'S", "Fragment loading...: ");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_id, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void displayItemList(List<HorizontalItems> dataList) {
        Log.i("CALLBACK'S", "my dataList: " + dataList);
        //fragmentInjection;
        fragmentInjection();
    }

}