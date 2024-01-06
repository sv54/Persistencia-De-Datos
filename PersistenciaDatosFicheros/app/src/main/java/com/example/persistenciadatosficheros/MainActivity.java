package com.example.persistenciadatosficheros;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.health.connect.datatypes.units.Length;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    boolean checkExternalStorage = false;
    Button externalStorage;
    Button internalStorage;
    File archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        archivo = new File(getFilesDir(), "archivo.txt");


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PDDM_Files");


        Button verEstadoAlmacenamientoExterno = findViewById(R.id.verEstadoAlmacenamiento);
        Button addToFile = findViewById(R.id.addToFile);
        Button seeFile = findViewById(R.id.seeFile);
        externalStorage = findViewById(R.id.externalStorage);
        internalStorage = findViewById(R.id.internalStorage);
        internalStorage.setEnabled(false);
        Button close = findViewById(R.id.close);

        EditText editText = findViewById(R.id.editText);

        close.setOnClickListener(v -> {
            finishAffinity();
        });

        verEstadoAlmacenamientoExterno.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EstadoAlmacenamientoActivity.class);
            startActivity(intent);
        });

        seeFile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VerFicheroActivity.class);
            intent.putExtra("ruta", archivo.getPath());
            System.out.println(archivo.getAbsolutePath());
            startActivity(intent);
        });

        externalStorage.setOnClickListener(v -> {
            if (checkPermisos()) {
//                moverAExternal();
                archivo = moveFile(getFilesDir().getPath() + "/", "archivo.txt", getExternalFilesDir(null).getPath() + "/" );
                externalStorage.setEnabled(false);
                internalStorage.setEnabled(true);
                showToast(archivo.getPath());

            }
        });

        internalStorage.setOnClickListener(v -> {
//            moverAInternal();
            archivo = moveFile(getExternalFilesDir(null).getPath() + "/", "archivo.txt", getFilesDir().getPath() + "/" );
            externalStorage.setEnabled(true);
            internalStorage.setEnabled(false);
            showToast(archivo.getPath());
        });

        addToFile.setOnClickListener(v -> {
            String texto = editText.getText().toString();
            if (!texto.isEmpty()) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(archivo, true);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

                    try {
                        outputStreamWriter.write(texto);
                        outputStreamWriter.write("\n");
                    } finally {
                        outputStreamWriter.close();
                    }
                    Toast.makeText(this, archivo.getPath(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error al guardar en el archivo", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "El texto está vacío", Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected boolean checkPermisos(){
        if (Build.VERSION.SDK_INT >= 30) {
            if(Environment.isExternalStorageManager() == false) {
                Uri uri = Uri.parse("package:" + "com.example.persistenciadatosficheros");
                startActivity(new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri));
                checkExternalStorage = true;
                return false;
            }
            else {
                return true;
            }
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (Build.VERSION.SDK_INT >= 30) {
            if(checkExternalStorage) {
                checkExternalStorage = false;
                if(Environment.isExternalStorageManager()) {
                    //moverAInternal();
                }
            }
        }
    }
    private File moveFile(String inputPath, String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        File outputFile = null;
        try {

            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            outputFile = new File(outputPath, inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            out.flush();
            out.close();
            out = null;

            new File(inputPath + inputFile).delete();


        }

        catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        return outputFile;

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}