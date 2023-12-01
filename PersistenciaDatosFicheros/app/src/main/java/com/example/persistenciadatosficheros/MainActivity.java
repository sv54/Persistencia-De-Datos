package com.example.persistenciadatosficheros;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PDDM_Files");

        Button verEstadoAlmacenamientoExterno = findViewById(R.id.verEstadoAlmacenamiento);
        Button addToFile = findViewById(R.id.addToFile);
        Button seeFile = findViewById(R.id.seeFile);
        Button externalStorage = findViewById(R.id.externalStorage);
        Button internalStorage = findViewById(R.id.internalStorage);
        Button close = findViewById(R.id.close);

        EditText editText = findViewById(R.id.editText);

        close.setOnClickListener(v -> {
            finishAffinity();
        });


    }
}