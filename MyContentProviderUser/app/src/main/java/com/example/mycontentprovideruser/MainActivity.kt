package com.example.mycontentprovideruser

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar

class MainActivity : AppCompatActivity() {
    lateinit var closeButton: Button
    lateinit var loginButton: Button
    lateinit var editUsername: EditText
    lateinit var editPassword: EditText

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        closeButton = findViewById<Button>(R.id.close)
        loginButton = findViewById(R.id.login)
        editUsername = findViewById(R.id.editUsername)
        editPassword = findViewById(R.id.editPassword)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Probando Content Provider"



        closeButton.setOnClickListener {
            finish()
            finishAffinity()
        }

        loginButton.setOnClickListener {
            login()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun login() {
        val username = editUsername.text.toString()
        val password = editPassword.text.toString()

        val contentResolver = contentResolver
        val uriDatos = Uri.parse("content://com.example.sqlitepractica.provider/datos")

        val selection = "nombre_usuario=? AND password=?"
        val selectionArgs = arrayOf(username, password)

        val cursor = contentResolver.query(uriDatos, arrayOf("nombre_completo", "nombre_usuario"), selection, selectionArgs, null)


        if (cursor != null && cursor.moveToFirst()) {
            val nombreCompletoIndex = cursor.getColumnIndex("nombre_completo")
            val usernameIndex = cursor.getColumnIndex("nombre_usuario")

            if (nombreCompletoIndex >= 0 && usernameIndex >= 0) {
                val nombreCompleto = cursor.getString(nombreCompletoIndex)
                val username = cursor.getString(usernameIndex)

                intent = Intent(this, UserDataActivity::class.java)
                intent.putExtra("NOMBRECOMPLETO", nombreCompleto)
                intent.putExtra("USERNAME", username)
                startActivity(intent)
            }
        } else {
            Toast.makeText(this, "Error usuario/password incorrectos", Toast.LENGTH_SHORT).show()
        }
    }
}