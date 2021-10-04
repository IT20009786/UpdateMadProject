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

import com.example.moremeal.Adapter.DataAdapter;
import com.example.moremeal.Interface.Callback;
import com.example.moremeal.Model_new.Pizza;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivityForUser extends AppCompatActivity implements Callback {

    RecyclerView recyclerView;
    ArrayList<Pizza> arrayList;
    DataAdapter adapter;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    EditText editSearch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_user);
        recyclerView = findViewById(R.id.recyclerView);
        editSearch = findViewById(R.id.editSearch);

        //Initialize and assign varibale
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.menu);

        //Performe ItemSelectedListner
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.offers:
                        startActivity(new Intent(getApplicationContext(), OffersActivityForUser.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu:
                        return true;

                }
                return false;
            }
        });






        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Item Loading...");
        databaseReference = FirebaseDatabase.getInstance().getReference("PizzaBook");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Pizza pizza = ds.getValue(Pizza.class);
                    pizza.setKey(ds.getKey());
                    arrayList.add(pizza);

                }
                adapter = new DataAdapter(MainActivityForUser.this, arrayList, MainActivityForUser.this);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivityForUser.this, "Error" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
                ArrayList<Pizza> pizzaArrayList = new ArrayList<>();
                for (Pizza p : arrayList) {

                    if (p.getName().toLowerCase().contains(itemName)) {
                        pizzaArrayList.add(p);

                    }

                    adapter.searchItemName(pizzaArrayList);

                }

            }
        });

    }


    public void uploadClick(View view) {

        startActivity(new Intent(MainActivityForUser.this, UploadActivity.class));
    }

    @Override
    public void onClick(int i) {

        Intent intent = new Intent(MainActivityForUser.this, DetailActivityForUser.class);
        intent.putExtra("IMAGE", arrayList.get(i).getImageUrl());
        intent.putExtra("NAME", arrayList.get(i).getName());
        intent.putExtra("DESCRIPTION", arrayList.get(i).getDescription());
        intent.putExtra("PRICE", arrayList.get(i).getPrice());
        intent.putExtra("KEY", arrayList.get(i).getKey());
        startActivity(intent);

    }
}