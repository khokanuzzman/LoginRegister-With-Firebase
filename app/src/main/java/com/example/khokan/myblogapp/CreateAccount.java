package com.example.khokan.myblogapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccount extends AppCompatActivity {
    private EditText email;
    private EditText pass;
    private  EditText conf_pass;
    private Button create_account;
    private ProgressBar myprogressbar;
    private FirebaseAuth mAuth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        
        
        mAuth= FirebaseAuth.getInstance();
        
        email = findViewById(R.id.email);
        pass =findViewById(R.id.password);
        conf_pass=findViewById(R.id.confirm_pass);
        create_account=findViewById(R.id.create_account_btn);
        myprogressbar=findViewById(R.id.reg_progress);

        
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String uEmail= email.getText().toString();
                String uPass= pass.getText().toString();
                String uconfPass= conf_pass.getText().toString();
                
                if (!TextUtils.isEmpty(uEmail)&& !TextUtils.isEmpty(uPass)&&!TextUtils.isEmpty(uconfPass))
                {
                    if (uPass.equals(uconfPass)){
                        myprogressbar.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(uEmail,uPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    sendToMain();
                                }else {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(CreateAccount.this, "Error: "+errorMessage, Toast.LENGTH_SHORT).show();
                                }
                                myprogressbar.setVisibility(View.INVISIBLE);
                            }
                        });
                        
                    }else {

                        Toast.makeText(CreateAccount.this, "Password Did not match", Toast.LENGTH_SHORT).show();
                        
                    }
                }
                
                
                
            }
        });
        
        
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser= mAuth.getCurrentUser();
        if (currentUser != null){
            sendToMain();
        }
        
    }

    private void sendToMain() {

        Intent intent =new Intent(CreateAccount.this, MainActivity.class);
        startActivity(intent);
        finish();
    
    }
}
