//package com.example.dailymenu.Auth.Login.View;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.dailymenu.Home.View.Home;
//import com.example.dailymenu.R;
//import com.example.dailymenu.Auth.Signup.signup;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.GoogleAuthProvider;
//
//public class login extends AppCompatActivity {
//    EditText email , pass;
//    Button login;
//    TextView create_acc;
//    ImageButton google;
//    private FirebaseAuth mAuth;
//    private GoogleSignInClient client;
//    TextView skip;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);
//        email = findViewById(R.id.et_log_email);
//        pass = findViewById(R.id.et_log_pass);
//        login = findViewById(R.id.btn_login);
//        create_acc = findViewById(R.id.tv_create_account);
//        google = findViewById(R.id.btn_go);
//        skip = findViewById(R.id.skip);
//        mAuth = FirebaseAuth.getInstance();
//        SharedPreferences sharedPreferences = getSharedPreferences("Logged" , Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.clientId))
//                .requestEmail()
//                .build();
//        client = GoogleSignIn.getClient(this,options);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String emailStr = email.getText().toString();
//                String passStr = pass.getText().toString();
//                if (emailStr.isEmpty())
//                {
//                    email.setError("Enter Email");
//                }
////                else if (Patterns.EMAIL_ADDRESS.matcher(emailStr).matches())
////                {
////                    email.setError("Enter Valid Email");
////                }
//                else if(passStr.isEmpty())
//                {
//                    pass.setError("Enter Password");
//                }
//                else {
//                    mAuth.signInWithEmailAndPassword(emailStr, passStr)
//                            .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(login.this , Home.class));
//                                        finish();
//                                    } else {
//
//                                        Toast.makeText(login.this, "Email or Password incorrect",
//                                                Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//
//                }
//            }
//        });
//        create_acc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(login.this , signup.class));
//            }
//        });
//        google.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = client.getSignInIntent();
//                startActivityForResult(i,123);
//            }
//        });
//        skip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editor.putBoolean("isLogin" , false);
//                editor.commit();
//
//            }
//        });
//    }
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 123){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//
//                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
//                FirebaseAuth.getInstance().signInWithCredential(credential)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(login.this , Home.class));
//                                    finish();
//
//                                }else {
//                                    Toast.makeText(login.this, "There is Problem Try Again", Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        });
//
//            } catch (ApiException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }
//}