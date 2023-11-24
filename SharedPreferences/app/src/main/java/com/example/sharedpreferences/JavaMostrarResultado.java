package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JavaMostrarResultado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_mostrar_resultado);

        Button botonVolver = findViewById(R.id.BotonVolver);
        TextView texto = findViewById(R.id.textoMostrar);

        SharedPreferences misPrefs = getSharedPreferences("misPref", Context.MODE_PRIVATE);

        texto.setTextSize(Float.parseFloat(decrypt(misPrefs.getString("FontSize", encrypt("50")))));
        texto.setText(decrypt(misPrefs.getString("Texto", encrypt("null"))));

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public String encrypt(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }
    public String decrypt(String input) {
        return new String(Base64.decode(input.getBytes(), Base64.DEFAULT));
    }
}