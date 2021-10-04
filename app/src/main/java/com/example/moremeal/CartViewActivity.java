package com.example.moremeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Model> list;
    MyAdapter myAdapter;
    DatabaseReference dbRef;
    Model ob;
    private static Context context;
    ArrayList<String> myList;



    public static  final String EXTRA_MESSAGE= "com.example.myfirstapp.MESSAGE";
    public static  final String EXTRA_MESSAGE1= "com.example.myfirstapp.MESSAGE1";
    public static  final String EXTRA_MESSAGE2= "com.example.myfirstapp.MESSAGE2";
    public static  final String EXTRA_MESSAGE3= "com.example.myfirstapp.MESSAGE3";
    public static  final String EXTRA_MESSAGE4= "com.example.myfirstapp.MESSAGE4";
    public static  final String EXTRA_MESSAGE5= "com.example.myfirstapp.MESSAGE5";
    public static  final String EXTRA_MESSAGE6= "com.example.myfirstapp.MESSAGE6";
    public static  final String EXTRA_MESSAGE7= "com.example.myfirstapp.MESSAGE7";

    //variables for store values which are got by intent object
    //String quantity;
    //String price;  //price according to the pizza size
    //String pizzaSize;
    //String pizzaName;

    /////modification
    // public CartViewActivity(){};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_cart_view);

        context = this;
        //new Helper(context);

        ImageView rightIcon = findViewById(R.id.right_icon);
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context cnn = getMyContext();
                Intent intent2;
                intent2 = new Intent(cnn, CartViewActivity.class);
                cnn.startActivity(intent2);



            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        list = new ArrayList<>();
        myList = new ArrayList<>();
        dbRef = FirebaseDatabase.getInstance().getReference("Model");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Double total = 0.00;
                for(DataSnapshot ds: snapshot.getChildren()){

                    String key = ds.getKey();
                    //myList.add(key);
                    Model obj = ds.getValue(Model.class);
                    obj.setMyd(key);
                    list.add(obj);

                }


                for(int i=0; i<list.size(); i++){

                    total = total + list.get(i).getNowPrice();
                }


                TextView textView = (TextView) findViewById(R.id.tprice);
                String tt = " "+total;
                textView.setText(tt);

                if (total == 0.00){

                    TextView notify = (TextView)findViewById(R.id.textViewNotify);
                    notify.setText("Your cart is Empty..Add some foods.");
                }


                myAdapter = new MyAdapter(list);
                recyclerView.setAdapter(myAdapter);

                Button myButton = findViewById(R.id.buttonPurchase);
                myButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context cnt = getMyContext();
                        Intent myIntent = new Intent(cnt, AfterCartActivity.class);
                        myIntent.putExtra(EXTRA_MESSAGE, tt);
                        cnt.startActivity(myIntent);

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // populateList();
        // myAdapter = new MyAdapter(this, list);
        // recyclerView.setAdapter(myAdapter);
        // recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    /*
    private void populateList() {
        list = new ArrayList<>();
        list.add(new Model("pizza", R.drawable.pizza, 3,  6900.00, pizzaSize));
        list.add(new Model("pizza", R.drawable.pizza, 3,  6900.00, pizzaSize));
        list.add(new Model("pizza", R.drawable.pizza, 3, 6900.00, pizzaSize));
        list.add(new Model("pizza", R.drawable.pizza, 3, 6900.00, pizzaSize));
        list.add(new Model("pizza", R.drawable.pizza, 3, 6900.00, pizzaSize));

    }

*/


    public  void insert(String pizzaName, Integer imgId, Integer quantity, Double price, String size, Double medium, Double large, Double premium){

        dbRef = FirebaseDatabase.getInstance().getReference().child("Model");
        // Integer s= insert2();
        try {
            //Double mpr = 6900.00+s;
            ob.setName(pizzaName);
            ob.setImage(R.drawable.pizza);
            ob.setQuantity(quantity);
            Double tot = price * quantity;
            ob.setNowPrice(tot);
            ob.setNowSize(size);
            ob.setMediumPrice(medium);
            ob.setLargePrice(large);
            ob.setPremiumPrice(premium);
            ob.setMyd("hello");

            // ob.setNowPrice(mpr);



            dbRef.push().setValue(ob);
            // dbRef.child("").setValue(ob);
            Toast.makeText(getApplicationContext(), "Item added Successfully", Toast.LENGTH_SHORT).show();



        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
        }


    }


    public Integer insert2() {

        final Integer[] mySize = {0};
        DatabaseReference dbRef2 = FirebaseDatabase.getInstance().getReference("Model");
        // DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Model");



        dbRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            // @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer size = 0;

                ArrayList<Model> test = new ArrayList<>();

                for(DataSnapshot ds: snapshot.getChildren()){

                    Model obj = ds.getValue(Model.class);
                    test.add(obj);

                }

                mySize[0] = test.size();


            }

            // @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return mySize[0];
    }



    public static Context getMyContext(){

        // Context con = getApplicationContext();
        return context;
    }

    public  void purchaseAll(View view){

        Intent myIntent = new Intent(this, AfterCartActivity.class);
        myIntent.putExtra(EXTRA_MESSAGE, "");


    }

    /////modification
    //public  TextView getMtTextView(){

    //TextView  totTextView = (TextView)findViewById(R.id.tprice);
    //return  totTextView;

    //}



   /* public class Helper {
        Context mContext;
        Helper(Context ctx){
            this.mContext= ctx;}
        }*/

}