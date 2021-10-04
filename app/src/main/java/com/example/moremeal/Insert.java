package com.example.moremeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Insert extends AppCompatActivity {

    EditText feedbck;
    Button button;

    DatabaseReference referenc;
    Feedbackdb Re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        button = findViewById(R.id.sbFeedback);
        feedbck = findViewById(R.id.feedbck);

        Re = new Feedbackdb();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                referenc = FirebaseDatabase.getInstance().getReference().child("Feedback");

                Re.setFeedback(feedbck.getText().toString().trim());

                referenc.child("feed1").setValue(Re);
                Toast.makeText(getApplicationContext(), "Added Successfull!..", Toast.LENGTH_SHORT).show();

            }
        });

    }
}