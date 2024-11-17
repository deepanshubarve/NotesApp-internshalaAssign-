package com.example.internshalaassign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
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
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs =getSharedPreferences("userdata",MODE_PRIVATE);
        String name= prefs.getString("name",null);
        String email=prefs.getString("email",null);
        String picture=prefs.getString("picture",null);

        if(name!=null){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("email",email);
            intent.putExtra("name",name);
            intent.putExtra("picture",picture);
            startActivity(intent);
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Button signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(
            Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);

        } catch (ApiException e) {

        }
    }
    private void updateUI (GoogleSignInAccount account) {
        if (account != null) {

            String email = account.getEmail();
            String name = account.getDisplayName();
            String picture = String.valueOf(account.getPhotoUrl());
            SharedPreferences.Editor editor =getSharedPreferences("userdata",MODE_PRIVATE).edit();
            editor.putString("name",account.getDisplayName());
            editor.putString("email", account.getEmail());
            editor.putString("picture",picture);
            editor.apply();

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("name", name);
            intent.putExtra("picture", picture);
            startActivity(intent);
        }


    }
}