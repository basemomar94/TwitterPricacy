package com.example.twitterpricacy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class Preference extends AppCompatActivity {
    Button gotoDic;
    Switch words, tag, mentions, location;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);
        db = FirebaseFirestore.getInstance();


        gotoDic = findViewById(R.id.gotoDic);
        words = findViewById(R.id.wordsSwitch);
        tag = findViewById(R.id.tagSwitch);
        mentions = findViewById(R.id.mentionsSwitch);
        location = findViewById(R.id.locationSwitch);

        gotoDic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Preference.this, DictionaryActivity.class);
                startActivity(intent);
            }
        });


    }


    void getSettingsUpdates(String userId) {
        db.collection("userId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                }
            }
        });


    }


}
