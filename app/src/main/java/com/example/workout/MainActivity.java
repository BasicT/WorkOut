package com.example.workout;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.kongzue.dialog.v3.MessageDialog;
import android.view.View;

import org.litepal.LitePal;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static MainActivity mainActivity = null;
    private TextAdapter adapter;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        List<Text> texts = LitePal.select("title").find(Text.class);
       /*final List<Text> texts1 = LitePal.findAll(Text.class);
        int i = texts.size();
        String[] data = new String[i];
        for (Text text : texts){
            data[texts.size()-i] = text.getTitle();
            i = i-1;
        }*/
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TextAdapter(texts);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });
    }

    public TextAdapter getAdapter() {
        return adapter;
    }
}
