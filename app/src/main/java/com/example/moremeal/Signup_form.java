package com.example.moremeal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_form extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText address;
    EditText email;
    EditText password;
    Button   register;

    DatabaseReference dbRef;
    RegisterDetails std;

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        mAuth = FirebaseAuth.getInstance();

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);

        std = new RegisterDetails();

        Intent i = getIntent();//from login form

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkDataEntered();


                // Check if user is signed in (non-null) and update UI accordingly.
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser != null){
                    reload();
                }



            }
        });
    }

    // [END on_start_check_user]
    private void createAccount( ) {

        String emailV = email.getText().toString();
        String passwordV = password.getText().toString();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(emailV, passwordV)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            sendEmailVerification();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Signup_form.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }


    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Email sent
                    }
                });
        // [END send_email_verification]
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }




    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private void checkDataEntered() {

        boolean isValid = true;

        if (isEmpty(firstName)) {
            Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
            t.show();
            isValid = false;
        }

        if (isEmpty(firstName)) {
            firstName.setError("First name is required!");
            isValid = false;
        }


        if (isEmpty(lastName)) {
            lastName.setError("Last name is required!");
            isValid = false;
        }

        if (isEmpty(address)) {
            address.setError("address is required!");
            isValid = false;
        }

        if (isEmpty(password)) {
            password.setError("password is required!");
            isValid = false;
        }

        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
            isValid = false;
        }


        if (isValid) {

            if (isEmail(email)) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Register");

                std.setFirstName(firstName.getText().toString().trim());
                std.setLastName(lastName.getText().toString().trim());
                std.setPostalAddress(address.getText().toString().trim());
                std.setEmail(email.getText().toString().trim());
                std.setPassword(password.getText().toString().trim());


                //Insert into DataBase
                dbRef.push().setValue(std);
                createAccount( ); // create account using email and password

                //everything checked will open Login form
                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent register = new Intent(Signup_form.this, Login_form.class);
                        startActivity(register);


                    }
                });

                Toast.makeText(getApplicationContext(), "Successfully Registered ", Toast.LENGTH_SHORT).show();

                //close the activity
                this.finish();


            } else {

                Toast t = Toast.makeText(this, "Wrong email or password ", Toast.LENGTH_SHORT);
                t.show();

            }
        }

    }


}