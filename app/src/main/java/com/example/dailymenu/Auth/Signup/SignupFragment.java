package com.example.dailymenu.Auth.Signup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailymenu.R;
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

public class SignupFragment extends Fragment {

    EditText email , pass , confirmPass;
    TextView havaAccount;
    Button signup;
    ImageButton googleAuth;
    private FirebaseAuth mAuth;
    private GoogleSignInClient client;
    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.et_sign_email);
        pass = view.findViewById(R.id.et_sign_pass);
        confirmPass = view.findViewById(R.id.et_confirm_pass);
        signup = view.findViewById(R.id.btn_signup);
        havaAccount = view.findViewById(R.id.tv_have_acc);
        googleAuth = view.findViewById(R.id.btn_google);
        mAuth =  FirebaseAuth.getInstance();
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.clientId))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(requireContext(),options);
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
                            .addOnCompleteListener((Activity) requireContext(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(requireContext(), "Sign up Successfully", Toast.LENGTH_SHORT).show();
                                       // startActivity(new Intent(requireContext() , login.class));
                                        Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_loginFragment);
                                    } else {

                                        Toast.makeText(requireContext(), "Sign up failed...Try Again",
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
                //startActivity(new Intent(requireContext() , login.class));
                Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_loginFragment);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                                    Navigation.findNavController(getView()).navigate(R.id.action_signupFragment_to_loginFragment);
                                    Toast.makeText(requireContext(), "SignUp Successfully", Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }

    }
}