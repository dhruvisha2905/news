package com.example.android.newsfeed;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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


public class SignupActivity extends AppCompatActivity {
    EditText eusername, eemail, epassword;
    Button register;
    boolean isAllFields = false;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        eusername = findViewById(R.id.username);
        eemail = findViewById(R.id.email);
        epassword = findViewById(R.id.password);
        register = findViewById(R.id.regibutton);

        //check if user is logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(SignupActivity.this,MainActivity.class);
            startActivity(i);
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)

            {
                isAllFields= register();
                if(isAllFields)
                {

                    String a= eusername.getText().toString();
                    String b = epassword.getText().toString();
                    Intent i = new Intent(SignupActivity.this,MainActivity.class);
                    i.putExtra("number1",a);
                    i.putExtra("number2",b);
                    startActivity(i);

                }



            }
        });


    }

    private boolean register() {
        String username = eusername.getText().toString().trim();
        String email = eemail.getText().toString().trim();
        String password = epassword.getText().toString().trim();
        if (CheakAllField(username, email, password)) {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(SignupActivity.this, "User register",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignupActivity.this, "Something went wrong",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
            return true;
        }
        return false;
    }

    private boolean CheakAllField (String username, String email, String password)
    {
        if (TextUtils.isEmpty(username)) {
            eusername.setError("Please enter name");
            return false;

        }

        if (TextUtils.isEmpty(email)) {
            eemail.setError("please enter proper email");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            epassword.setError("please enter proper password");
            return false;
        }
        return true;
    }


    public void LoginBtnClick(View view) {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}

