package com.example.moremeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRiders extends AppCompatActivity {

    //variables
    EditText name, mobile ,email, address, vehNumber;
    Button subBtn , UpdBtn;

    DatabaseReference reference;
    AddRiderdb Ri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_riders);

        name = findViewById(R.id.txtName);
        mobile = findViewById(R.id.txtMobile);
        email =findViewById(R.id.txtEmail);
        address=findViewById(R.id.txtAdress);
        vehNumber = findViewById(R.id.txtVehicle);
        subBtn = findViewById(R.id.btnupdate);

        Ri = new AddRiderdb();

        subBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //intent

                reference = FirebaseDatabase.getInstance().getReference().child("AddRiders");


                if (TextUtils.isEmpty(name.getText().toString()))
                    Toast.makeText(getApplicationContext(),  "Please enter the Name " , Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(mobile.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter the Mobile " , Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(email.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter the " , Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(address.getText().toString()))
                    Toast.makeText(getApplicationContext(),  "Please enter the " , Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(vehNumber.getText().toString()))
                    Toast.makeText(getApplicationContext(),  "Please enter the " , Toast.LENGTH_SHORT).show();
                else {


                    Ri.setName(name.getText().toString().trim());
                    Ri.setAddress(address.getText().toString().trim());
                    Ri.setEmail(email.getText().toString().trim());
                    Ri.setVehicleNum(vehNumber.getText().toString().trim());
                    Ri.setMobile(mobile.getText().toString().trim());

                    reference.child("rider1").setValue(Ri);
                    Toast.makeText(getApplicationContext(), "Added Successfull!..", Toast.LENGTH_SHORT).show();

                    /// intent start

                }
            }

        });

    }
}