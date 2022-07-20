package com.example.twitterpricacy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DictionaryActivity extends AppCompatActivity {

    MyRecyclerViewAdapter adapter;
    ArrayList<String> wordsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);


        // data to populate the RecyclerView with

        wordsList.add("Horse");
        wordsList.add("Cow");
        wordsList.add("Camel");
        wordsList.add("Sheep");
        wordsList.add("Goat");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.wordsRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, wordsList);
        recyclerView.setAdapter(adapter);

        Button addButton = findViewById(R.id.addBu);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addnewWord();
            }
        });

    }


    void addnewWord() {
        TextView newWordTv = findViewById(R.id.newwordTv);
        String newWord = newWordTv.getText().toString();
        wordsList.add(newWord);
        adapter.notifyItemInserted(wordsList.size());
        newWordTv.setText("");


    }
}