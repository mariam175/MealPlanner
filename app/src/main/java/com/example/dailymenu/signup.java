package com.example.dailymenu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class signup extends AppCompatActivity {
    EditText email , pass , confirmPass;
    TextView havaAccount;
    Button signup;
    ImageButton googleAuth;
    private FirebaseAuth mAuth;
    private GoogleSignInClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        email = findViewById(R.id.et_sign_email);
        pass = findViewById(R.id.et_sign_pass);
        confirmPass = findViewById(R.id.et_confirm_pass);
        signup = findViewById(R.id.btn_signup);
        havaAccount = findViewById(R.id.tv_have_acc);
        googleAuth = findViewById(R.id.btn_google);
        mAuth =  FirebaseAuth.getInstance();
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.clientId))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this,options);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = email.getText().toString();
                String passStr = pass.getText().toString();
                String confirmPassStr = confirmPass.getText().toString();
                if (emailStr.isEmpty())
                {
                    email.setError("Enter Email");
                }
//                else if (Patterns.EMAIL_ADDRESS.matcher(emailStr).matches())
//                {
//                    email.setError("Enter Valid Email");
//                }
                else if(passStr.isEmpty())
                {
                    pass.setError("Enter Password");
                }
                else if (!confirmPassStr.equals(passStr))
                {
                    confirmPass.setError("Must Equal Password");
                }
                else {
                    mAuth.createUserWithEmailAndPassword(emailStr, passStr)
                            .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(signup.this, "Sign up Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(signup.this , login.class));
                                    } else {

                                        Toast.makeText(signup.this, "Sign up failed...Try Again",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });
        havaAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this , login.class));
            }
        });
        googleAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i,1234);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(signup.this, "SignUp Successfully", Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }

    }
}