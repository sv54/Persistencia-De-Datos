package com.example.persistenciadatosficheros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class VerFicheroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_fichero);

        Button volver = findViewById(R.id.volverFichero);
        TextView ficheroText = findViewById(R.id.fichero);

        Intent intent = getIntent();
        String filePath = intent.getStringExtra("ruta");
        File archivo = new File(filePath);

        StringBuilder contenido = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String textoArchivo = contenido.toString();

        ficheroText.setText(textoArchivo);

        volver.setOnClickListener(v -> {
            finish();
        });


    }


}