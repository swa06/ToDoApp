package com.washfi.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import static com.washfi.todoapp.AppConstant.DESCRIPTION;
import static com.washfi.todoapp.AppConstant.TITLE;

public class DetailActivity extends AppCompatActivity {

    TextView textViewTitle, textViewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bindView();
        setUpIntentData();
    }

    private void setUpIntentData() {
        Intent intent = getIntent();
        textViewTitle.setText(intent.getStringExtra(TITLE));
        textViewDescription.setText(intent.getStringExtra(DESCRIPTION));
    }

    private void bindView() {
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);
    }
}