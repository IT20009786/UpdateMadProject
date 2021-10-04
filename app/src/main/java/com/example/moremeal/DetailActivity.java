package com.example.moremeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView tvItemName;
    TextView tvDescription;
    TextView tvPrice;
    String imageUrl;
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView = findViewById(R.id.image);
        tvItemName = findViewById(R.id.nameitem);
        tvDescription = findViewById(R.id.itemDescription);
        tvPrice = findViewById(R.id.itemPrice);


        Intent intent = getIntent();

        imageUrl = intent.getStringExtra("IMAGE");

        Glide.with(this).load(imageUrl).into(imageView);

        String itemName = intent.getStringExtra("NAME");

        tvItemName.setText(itemName);

        String itemDescription = intent.getStringExtra("DESCRIPTION");

        tvDescription.setText(itemDescription);

        String itemPrice = intent.getStringExtra("PRICE");

        tvPrice.setText(itemPrice);


        key = intent.getStringExtra("KEY");


    }

    public void deleteItem(View view) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PizzaBook");
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference= storage.getReferenceFromUrl(imageUrl);

        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                reference.child(key).removeValue();
                Toast.makeText(DetailActivity.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailActivity.this, MainActivity.class));
                finish();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(DetailActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}