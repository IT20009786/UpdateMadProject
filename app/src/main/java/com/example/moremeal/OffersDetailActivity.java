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

public class OffersDetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView tvItemName;
    TextView tvDescription;
    String imageUrl;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_detail);
        imageView = findViewById(R.id.image);
        tvItemName = findViewById(R.id.nameitem);
        tvDescription = findViewById(R.id.itemDescription);

        Intent intent= getIntent();

        imageUrl = intent.getStringExtra("IMAGE");

        Glide .with(this).load(imageUrl).into(imageView);

        String itemName=intent.getStringExtra("NAME");

        tvItemName.setText(itemName);

        String itemDescription = intent.getStringExtra("DESCRIPTION");

        tvDescription.setText(itemDescription);


        key = intent.getStringExtra("KEY");

    }

    public void deleteOffer(View view) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("OffersBook");
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference=storage.getReferenceFromUrl(imageUrl);

        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                reference.child(key).removeValue();
                Toast.makeText(OffersDetailActivity.this, "Data Deleted Successfully! ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OffersDetailActivity.this,OffersActivity.class));
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OffersDetailActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}