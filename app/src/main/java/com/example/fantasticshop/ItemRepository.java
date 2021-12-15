package com.example.fantasticshop;

import static com.example.fantasticshop.SingletonClass.getMyInstance;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fantasticshop.adapter.ItemAdapter;
import com.example.fantasticshop.fragments.HorizontalItems;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ItemRepository{

    CallBackInterface callBackInterface;

    public ItemRepository() {

    }

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }

    // Let's connect our items database reference

    DatabaseReference myDataBaseRef = getMyInstance().getReference();

    // we are now going to create a singleton list containing all our items
    List<HorizontalItems> itemList = getMyInstance().getItemList();

    // we are now going to update the database to the items list
    public void updateData(){

        myDataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //empty the itemList before data loading
                itemList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    HorizontalItems item = ds.getValue(HorizontalItems.class);

                    if(item != null) itemList.add(item);
                }
//                callback called
                callBackInterface.displayItemList(itemList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    // we now update the item on the database
    public void updateItem(HorizontalItems item){
        //we get the child from the database by it id and then set it
        myDataBaseRef.child(item.getId()).setValue(item);

    }


    public void deleteItem(HorizontalItems item) {
        myDataBaseRef.child(item.getId()).removeValue();
    }


}
