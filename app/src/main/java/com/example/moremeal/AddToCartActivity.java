package com.example.moremeal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddToCartActivity extends AppCompatActivity {


    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE1 = "com.example.myfirstapp.MESSAGE1";
    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "com.example.myfirstapp.MESSAGE3";
    public static final String EXTRA_MESSAGE4 = "com.example.myfirstapp.MESSAGE4";
    public static final String EXTRA_MESSAGE5 = "com.example.myfirstapp.MESSAGE5";
    public static final String EXTRA_MESSAGE6 = "com.example.myfirstapp.MESSAGE6";
    public static final String EXTRA_MESSAGE7 = "com.example.myfirstapp.MESSAGE7";

    String medium_price = "900.00";
    String large_price = "1500.00";
    String premium_price = "2300.00";
    String quantity;
    String price = ""; //price according to the size
    String pizzaSize;
    String pizzaName = "Bacon Pizza"; //also this is sent by him
    DatabaseReference dbRef;
    Model ob;
    Integer quan;
    Double rightPrice;
    Double price1;
    Double price2;
    Double price3;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);


        ob = new Model();
        context = this;

        ImageView rightIcon = findViewById(R.id.right_icon);
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context cnn = getAddToCartContext();
                Intent intent2;
                intent2 = new Intent(cnn, CartViewActivity.class);
                cnn.startActivity(intent2);


            }
        });


    }


    public void addToCart(View view) {

        //Do something in response to button
        Intent intent;
        intent = new Intent(this, CartViewActivity.class);

        EditText editText = (EditText) findViewById(R.id.editTextQuantity);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.pizza_size);


        int selectedId = radioGroup.getCheckedRadioButtonId();

        quantity = editText.getText().toString();

        if (selectedId == -1) {

            Toast.makeText(getApplicationContext(), "Please select a pizza size", Toast.LENGTH_SHORT).show();
        } else if (quantity.equals("0")) {


            Toast.makeText(getApplicationContext(), "Please select a quantity", Toast.LENGTH_SHORT).show();
        } else {

            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            pizzaSize = radioButton.getText().toString();
            //quantity   = editText.getText().toString();


            if (pizzaSize.equals("Medium")) {


                price = medium_price;

            } else if (pizzaSize.equals("Large")) {

                price = large_price;

            } else {

                price = premium_price;
            }


            quan = Integer.parseInt(quantity);
            rightPrice = Double.parseDouble(price);
            price1 = Double.parseDouble(medium_price);
            price2 = Double.parseDouble(large_price);
            price3 = Double.parseDouble(premium_price);

            insert(pizzaName, 1, quan, rightPrice, pizzaSize, price1, price2, price3);


            startActivity(intent);


        }


    }


    public void insert(String pizzaName, Integer imgId, Integer quantity, Double price, String size, Double medium, Double large, Double premium) {

        dbRef = FirebaseDatabase.getInstance().getReference().child("Model");
        // Integer s= insert2();
        try {
            //Double mpr = 6900.00+s;
            ob.setName(pizzaName);
            ob.setImage(R.drawable.pizza3);
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


        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
        }


    }


    public static Context getAddToCartContext() {

        // Context con = getApplicationContext();
        return context;
    }


    public Integer addToCartTest(Integer q, Integer radioId, String size, String pr1, String pr2, String pr3) {

        Integer right = 0;

        Integer selectedId = radioId;

        Integer quanti = q;

        String tPrice = "";

        pizzaSize = size;

        if (selectedId == -1) {

            return  1;
        } else if (quanti == 0) {


            return  2;
        } else {


            if (pizzaSize.equals("Medium")) {


                tPrice = pr1;

            } else if (pizzaSize.equals("Large")) {

                tPrice = pr2;

            } else {

                tPrice = pr3;
            }



            right = Integer.parseInt(tPrice) * quanti;

            Integer pric1 = Integer.parseInt(pr1);
            Integer pric2 = Integer.parseInt(pr2);
            Integer pric3 = Integer.parseInt(pr3);








        }

        return right;
    }












}