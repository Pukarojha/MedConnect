package com.example.medconnect;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.medconnect.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    String TAG = "MedConnect";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);

        ActivityMainBinding binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Initializing Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();


    }

    //signUp
    public void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "create ")
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        //if the sign in fails, display an error message to the user
                        Log.w(TAG, "createUserWithEmail: failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                })
    }

    //login
    public void login(String email, String password) {
        mAuth.createUserWithEmailAndPassword(this, new MediaPlayer.OnCompletionListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult>)
        })
    }

    //upload Image
    public void uploadImage(Uri fileUri, String userId) {
        StorageReference storageRef = storage.getReference().child("prescriptions/" + userId + ".jpg");

        storageRef.putFile(fileUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Image uploaded successfully
                })
                .addOnFailureListener(e -> {
                    // Handle error
                });
    }
    //HealthProfile
    public void saveHealthProfile(String userId, String allergies, String medications) {
        Map<String, Object> healthProfile = new HashMap<>();
        healthProfile.put("allergies", allergies);
        healthProfile.put("medications", medications);

        db.collection("users").document(userId)
                .set(healthProfile)
                .addOnSuccessListener(aVoid -> {
                    // Data saved successfully
                })
                .addOnFailureListener(e -> {
                    // Handle error
                });
    }

}