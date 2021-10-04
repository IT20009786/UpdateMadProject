package com.example.moremeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AfterCartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_cart);

        Intent intent = getIntent();
        String message = intent.getStringExtra(AddToCartActivity.EXTRA_MESSAGE);
        String modified = "Rs."+message+"//";

        TextView textView = findViewById(R.id.textView7);
        textView.setText(modified);


    }
}