package com.example.persistenciadatosficheros;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class EstadoAlmacenamientoActivity extends AppCompatActivity {

    boolean checkExternalStorage = false;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_almacenamiento);

        Button botonVolver = findViewById(R.id.volver);
        TextView info = findViewById(R.id.infoText);




        botonVolver.setOnClickListener(v -> {
            finish();
        });

        info.setText("State: " + Environment.getExternalStorageState() + "\n" +
                "Emulated: " + Environment.isExternalStorageEmulated() +  "\n" +
                "Removable: " + Environment.isExternalStorageRemovable()+  "\n" +
                "Legacy: "+ Environment.isExternalStorageLegacy() +"\n" +
                "Data Dir: "+ Environment.getDataDirectory().getPath() +"\n" +
                "Cache Dir: "+ Environment.getDownloadCacheDirectory().getPath() +  "\n");


        File[] archivos = Environment.getExternalStorageDirectory().listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    String line = "External " + archivo.getName() + " Dir: " + archivo.getPath() + "\n";
                    info.setText(info.getText() + line) ;
                }
            }
        }


        String rootDir = Environment.getRootDirectory().getPath();
        info.setText(info.getText() + "Root Dir: " + rootDir);

    }
}