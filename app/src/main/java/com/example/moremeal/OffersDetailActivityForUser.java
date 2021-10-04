package com.example.moremeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OffersDetailActivityForUser extends AppCompatActivity {

    ImageView imageView;
    TextView tvItemName;
    TextView tvDescription;
    String imageUrl;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_detail_for_user);
        imageView = findViewById(R.id.image);
        tvItemName = findViewById(R.id.nameitem);
        tvDescription = findViewById(R.id.itemDescription);

        //Initialize and assign varibale
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.offers);

        //Performe ItemSelectedListner
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.offers:
                        return true;
                    case R.id.menu:
                        startActivity(new Intent(getApplicationContext(), MainActivityForUser.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });

        Intent intent= getIntent();

        imageUrl = intent.getStringExtra("IMAGE");

        Glide .with(this).load(imageUrl).into(imageView);

        String itemName=intent.getStringExtra("NAME");

        tvItemName.setText(itemName);

        String itemDescription = intent.getStringExtra("DESCRIPTION");

        tvDescription.setText(itemDescription);


        key = intent.getStringExtra("KEY");

    }


}