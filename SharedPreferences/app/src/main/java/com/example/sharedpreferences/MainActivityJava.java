package com.example.sharedpreferences;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivityJava extends AppCompatActivity {

    SeekBar seekBar;
    TextView textoValores;
    EditText edit;
    TextView textSeekBar;
    Button botonAplicar;

    ImageButton botonKotlin;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_java);

        seekBar = findViewById(R.id.seekBar);
        textoValores = findViewById(R.id.valoresActuales);
        edit = findViewById(R.id.editTexto);
        textSeekBar = findViewById(R.id.valorSeekBar);
        botonAplicar = findViewById(R.id.aplicar);
        botonKotlin = findViewById(R.id.botonKotlin);

        seekBar.setMin(1);
        seekBar.setMax(50);
        seekBar.setProgress(32);
        textSeekBar.setText("Tamaño(" + seekBar.getProgress() + "/50)");
        edit.setText("Texto por defecto");

        SharedPreferences pref = getSharedPreferences("misPref", Context.MODE_PRIVATE);
        if(pref.contains("FontSize")){
            seekBar.setProgress(Integer.parseInt(decrypt(pref.getString("FontSize", encrypt("1")))));
            textSeekBar.setText("Tamaño(" + seekBar.getProgress() + "/50)");

            edit.setText(decrypt(pref.getString("Texto", encrypt("null"))));
        }

        updateTextValores();

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                updateTextValores();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Actualizar el EditText con el valor actual del SeekBar
                textSeekBar.setText("Tamaño(" + progress + "/50)");
                updateTextValores();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        botonAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("FontSize", encrypt(Integer.toString(seekBar.getProgress())));
                editor.putString("Texto", encrypt(edit.getText().toString()));
                editor.apply();

                Intent intent = new Intent(MainActivityJava.this, JavaMostrarResultado.class);
                startActivity(intent);
            }
        });

        botonKotlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivityJava.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button botonSalir = findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    private void updateTextValores() {
        textoValores.setText("Valores actuales de la aplicacion:\n" +
                "Texto: " + edit.getText() + "\n" + "Tamaño: " + seekBar.getProgress());
    }

    public String encrypt(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }
    public String decrypt(String input) {
        return new String(Base64.decode(input.getBytes(), Base64.DEFAULT));
    }
}