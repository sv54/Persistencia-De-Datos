package com.example.sqlitepractica

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var closeButton: Button
    lateinit var loginButton: Button
    lateinit var editUsername: EditText
    lateinit var editPassword: EditText
    val dbHelper = DatabaseHelper(this)
    var checkExternalStorage = false
    lateinit var db: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        closeButton = findViewById<Button>(R.id.close)
        loginButton = findViewById(R.id.login)
        editUsername = findViewById(R.id.editUsername)
        editPassword = findViewById(R.id.editPassword)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "SQLite"



        closeButton.setOnClickListener {
            finish()
            finishAffinity()
        }

        db = dbHelper.writableDatabase


    }



    private fun insertarUsuario(db: SQLiteDatabase, nombreUsuario: String, password: String, nombreCompleto: String, email: String) {
        // Crear un ContentValues con los valores a insertar
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NOMBRE, nombreUsuario)
            put(DatabaseHelper.COLUMN_PASSWORD, password)
            put(DatabaseHelper.COLUMN_NOMBRE_COMPLETO, nombreCompleto)
            put(DatabaseHelper.COLUMN_EMAIL, email)
        }

        // Insertar el nuevo usuario en la tabla
        db.insert(DatabaseHelper.TABLE_NAME, null, values)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_createBackUp -> {

                // Realizar operaciones en la base de datos (ejemplo: insertar un usuario)
                if (checkPermisos()){
                    if (dbHelper.backupDatabase()) {
                        Toast.makeText(this, "Backup exitoso", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error al realizar el backup", Toast.LENGTH_SHORT).show()
                    }
                }
                // Realizar el respaldo de la base de datos

                return true
            }
            R.id.action_restoreBackUp -> {
                if (checkPermisos()){

//                    insertarUsuario(db, "Restoring", "123456", "Nombre Completo", "usuario@dominio.com")

                    if (dbHelper.restoreDatabase()) {

                        Toast.makeText(this, "Backup exitoso", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error al realizar el backup", Toast.LENGTH_SHORT).show()
                    }
                }
                return true
            }
            R.id.action_ManageUsers -> {
                insertarUsuario(db, "AntesDeRestore", "123456", "Nombre Completo", "usuario@dominio.com")

                return true
            }
            /*android.R.id.home -> {
                return true
            }*/
            else -> return super.onOptionsItemSelected(item)
        }
    }

    protected fun checkPermisos(): Boolean {
        return if (Build.VERSION.SDK_INT >= 30) {
            if (Environment.isExternalStorageManager() == false) {
                val uri = Uri.parse("package:" + "com.example.sqlitepractica")
                startActivity(Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri))
                checkExternalStorage = true
                false
            } else {
                true
            }
        } else true
    }

    override fun onRestart() {
        super.onRestart()
        if (Build.VERSION.SDK_INT >= 30) {
            if (checkExternalStorage) {
                checkExternalStorage = false
                if (Environment.isExternalStorageManager()) {
                    //moverAInternal();
                }
            }
        }
    }

}