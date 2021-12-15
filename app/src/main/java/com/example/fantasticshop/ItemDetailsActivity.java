package com.example.fantasticshop;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fantasticshop.fragments.HorizontalItems;
import com.google.gson.Gson;

public class ItemDetailsActivity extends AppCompatActivity implements DialogBox.DialogBoxListener {

    ImageView item_image, item_star_icon, close, delete;
    TextView item_name, item_description, item_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        item_image = findViewById(R.id.item_image_id);
        item_name = findViewById(R.id.item_name_id);
        item_description = findViewById(R.id.item_description_id);
        item_price = findViewById(R.id.item_price_id);
        item_star_icon = findViewById(R.id.unlike_id);
        delete = findViewById(R.id.delete_item_id);
        close = findViewById(R.id.close_id);

//        retrieve data from the intent
        HorizontalItems currentItem = getCurrentHorizontalItem();

        setupStarLike(currentItem);

        //we now set the current item on the details page
        Glide.with(getApplicationContext()).load(currentItem.getImageUrl()).into(item_image);
        item_name.setText(currentItem.getName());
        item_description.setText(currentItem.getDescription());
        item_price.setText(currentItem.getPrice());

        // close the item details page
        close.setOnClickListener(v -> ItemDetailsActivity.this.finish());

        // delete the item clicked
        delete.setOnClickListener(v -> {
//                openDialog();
            ItemRepository repo = new ItemRepository();
            repo.deleteItem(getCurrentHorizontalItem());
        });

         Log.i("CALLBACK'S", "my currentItem: " + currentItem.getName());

    }

    private void setupStarLike(@NonNull HorizontalItems currentItem) {
        if(currentItem.getLiked()){
           item_star_icon.setImageResource(R.drawable.ic_star_like);
       }else{
           item_star_icon.setImageResource(R.drawable.ic_star_unlike);
       }
    }

    // an open dialog box open when clicked on the delete btn
    private void openDialog() {
        DialogBox dialogBox = new DialogBox();
            dialogBox.show(getSupportFragmentManager(), "dialogBox");
            Log.i("CALLBACK'S", "dialogBox display ...: ");

    }

    @Override
    public void onYesBtnClick() {
        Log.i("CALLBACK'S", "item deleted: ");
//        ItemRepository repo = new ItemRepository();
//        repo.deleteItem(getCurrentHorizontalItem());

    }

    //current item deserialized to get it object.
    private HorizontalItems getCurrentHorizontalItem() {
        String gsonData = getIntent().getStringExtra("gsonData");
        return new Gson().fromJson(gsonData, HorizontalItems.class);
    }

}

