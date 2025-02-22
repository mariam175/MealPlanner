package com.example.dailymenu.Auth.Login.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.dailymenu.Auth.Login.Presenter.LoginPresenter;
import com.example.dailymenu.Network.MealRemoteDataSource;
import com.example.dailymenu.Network.Repositry;
import com.example.dailymenu.R;
import com.example.dailymenu.db.MealLocalDataSource;
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

public class LoginFragment extends Fragment {

    EditText email , pass;
    Button login;
    TextView create_acc;
    ImageButton google;
    private FirebaseAuth mAuth;
    private GoogleSignInClient client;
    TextView skip;
    LoginPresenter loginPresenter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = view.findViewById(R.id.et_log_email);
        pass = view.findViewById(R.id.et_log_pass);
        login = view.findViewById(R.id.btn_login);
        create_acc = view.findViewById(R.id.tv_create_account);
        google = view.findViewById(R.id.btn_go);
        skip = view.findViewById(R.id.skip);
        mAuth = FirebaseAuth.getInstance();
         sharedPreferences = requireContext().getSharedPreferences("Logged" , Context.MODE_PRIVATE);
         editor = sharedPreferences.edit();
        loginPresenter = new LoginPresenter(this , Repositry.getInstance(MealRemoteDataSource.getInstance() , MealLocalDataSource.getInstance(getContext())));
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.clientId))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(requireContext(),options);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = email.getText().toString();
                String passStr = pass.getText().toString();
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
                else {

                    int res = loginPresenter.login(getActivity() , emailStr , passStr);
                    if (res == 1)
                    {
                        loginPresenter.resoreData();
                        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homeFragment);
                        Toast.makeText(requireContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                        editor.putBoolean("isLogin" , true);
                        editor.commit();
                    }
                    else{
                        Toast.makeText(requireContext(), "Email or Password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(requireContext() , signup.class));
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i,123);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putBoolean("isLogin" , false);
                editor.commit();
//                startActivity(new Intent(requireContext() , Home.class));
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(requireContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homeFragment);
                                    editor.putBoolean("isLogin" , true);
                                    editor.commit();


                                }else {
                                    Toast.makeText(requireContext(), "There is Problem Try Again", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }

    }
}