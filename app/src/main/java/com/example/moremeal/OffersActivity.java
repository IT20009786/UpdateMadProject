package com.example.moremeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moremeal.Adapter.OffersDataAdapter;
import com.example.moremeal.Interface.OffersCallback;
import com.example.moremeal.Model_new.Offers;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OffersActivity extends AppCompatActivity implements OffersCallback {
    RecyclerView recyclerView;
    ArrayList<Offers> arrayList;
    OffersDataAdapter adapter;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    EditText editSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        recyclerView = findViewById(R.id.recyclerView);
        editSearch = findViewById(R.id.editSearch);

        //Initialize and assign varibale
        BottomNavigationView bottomNavigationView = findViewById(R.id.second_bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.editoffers);

        //Performe ItemSelectedListner
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.editoffers:
                        return true;
                    case R.id.editmenu:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Items Loading...");
        databaseReference = FirebaseDatabase.getInstance().getReference("OffersBook");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Offers offers = ds.getValue(Offers.class);
                    offers.setKey(ds.getKey());
                    arrayList.add(offers);

                }
                adapter = new OffersDataAdapter(OffersActivity.this, arrayList, OffersActivity.this);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OffersActivity.this, "Error" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String itemName = s.toString();
                ArrayList<Offers> offersArrayList = new ArrayList<>();
                for (Offers o : arrayList) {

                    if (o.getName().toLowerCase().contains(itemName)) {

                        offersArrayList.add(o);

                    }
                    adapter.searchItemName(offersArrayList);

                }

            }
        });


    }

    public void uploadOffersClick(View view) {
        startActivity(new Intent(OffersActivity.this, UploadOffersActivity.class));
    }

    @Override
    public void onClick(int i) {
        Intent intent = new Intent(OffersActivity.this, OffersDetailActivity.class);
        intent.putExtra("IMAGE", arrayList.get(i).getImageUrl());
        intent.putExtra("NAME", arrayList.get(i).getName());
        intent.putExtra("DESCRIPTION", arrayList.get(i).getDescription());
        intent.putExtra("KEY", arrayList.get(i).getKey());
        startActivity(intent);

    }
}