package com.example.sqlitepractica

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

        loginButton.setOnClickListener {
            login()
        }

        db = dbHelper.writableDatabase
        DatabaseHelper.DatabaseSingleton.db = db

    }

    fun login(){
        val username = editUsername.text.toString()
        val password = editPassword.text.toString()

        val selectUserQuery = "SELECT * FROM ${DatabaseHelper.TABLE_NAME} WHERE ${DatabaseHelper.COLUMN_NOMBRE}=? AND ${DatabaseHelper.COLUMN_PASSWORD}=?"
        val selectionArgs = arrayOf(username, password)

        val cursor = db.rawQuery(selectUserQuery, selectionArgs)

        try {
            if (cursor.moveToFirst()) {
                val columnIndexFullName = cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE_COMPLETO)
                if (columnIndexFullName != -1 ) {
                    intent = Intent(this, UserDataActivity::class.java)
                    val nombreCompleto = cursor.getString(columnIndexFullName)
                    intent.putExtra("NOMBRECOMPLETO", nombreCompleto)
                    intent.putExtra("USERNAME", username)
                    startActivity(intent)
                }

            } else {
                Toast.makeText(this, "Error usuario/password incorrectos", Toast.LENGTH_SHORT).show()
            }
        } finally {
            cursor.close()
        }

    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_createBackUp -> {

                if (checkPermisos()){
                    if (dbHelper.backupDatabase()) {
                        Toast.makeText(this, "Backup exitoso", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error al realizar el backup", Toast.LENGTH_SHORT).show()
                    }
                }

                return true
            }
            R.id.action_restoreBackUp -> {
                if (checkPermisos()){

                    if (dbHelper.restoreDatabase()) {

                        Toast.makeText(this, "Restore exitoso", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error al realizar el restore", Toast.LENGTH_SHORT).show()
                    }
                }
                return true
            }
            R.id.action_ManageUsers -> {
                intent = Intent(this, UserManagerActivity::class.java)
                startActivity(intent)
                return true
            }
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
                }
            }
        }
    }



}