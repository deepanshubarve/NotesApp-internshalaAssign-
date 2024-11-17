package com.example.internshalaassign;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class UserDetailsActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_details);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String name = intent.getStringExtra("name");
        String pictureUrl = intent.getStringExtra("picture");
        ImageView ivProfile = findViewById(R.id.iv_profile);
        Glide.with(this)
                .load(pictureUrl)
                .circleCrop()
                .into(ivProfile);


        Button logoutButton = findViewById(R.id.logout_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SharedPreferences.Editor editor = getSharedPreferences("userdata", MODE_PRIVATE).edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(UserDetailsActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

        });

    }
}