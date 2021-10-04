package com.example.moremeal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.moremeal.Model_new.Pizza;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class UploadActivity extends AppCompatActivity {
    Uri uri;
    ImageView imageView;
    EditText edName;
    EditText edDesc;
    EditText edPrice;
    String imageUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        imageView = findViewById(R.id.imageView);
        edName = findViewById(R.id.tvName);
        edDesc = findViewById(R.id.tvDesc);
        edPrice = findViewById(R.id.tvPrice);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }
        else
        {
            Toast.makeText(this, "Permissions Allowed", Toast.LENGTH_SHORT).show();
        }



    }

    public void pickImage(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            uri = data.getData();
            imageView.setImageURI(uri);
        }
        else{
            Toast.makeText(this, "You Have Not Picked Image", Toast.LENGTH_SHORT).show();
        }

    }

    public void uploadPizza(View view) {

        uploadImage();

    }



    public void uploadImage(){


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Image is Uploading ...");
        progressDialog.show();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("PizzaBook").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task task = taskSnapshot.getStorage().getDownloadUrl();
                while (!task.isSuccessful());
                Uri uriImage = (Uri) task.getResult();
                imageUrl = uriImage.toString();
                uploadPizza();
                progressDialog.dismiss();
                Toast.makeText(UploadActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UploadActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void uploadPizza(){


        String name = edName.getText().toString().trim();
        String description = edDesc.getText().toString().trim();
        String price = edPrice.getText().toString().trim();

        String timeStamp = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Pizza pizza= new Pizza(name,description,price,imageUrl);

        FirebaseDatabase.getInstance().getReference("PizzaBook").child(timeStamp)
                .setValue(pizza).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UploadActivity.this, "Success", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }

}