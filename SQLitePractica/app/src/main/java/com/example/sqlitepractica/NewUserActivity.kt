package com.example.sqlitepractica

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

class NewUserActivity : AppCompatActivity() {

    lateinit var db: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        actionBar?.title = "New User"

        val loginEdit = findViewById<EditText>(R.id.loginEdit)
        val passwordEdit = findViewById<EditText>(R.id.passwordEdit)
        val userNameEdit = findViewById<EditText>(R.id.userNameEdit)

        val backButton = findViewById<Button>(R.id.backButton3)
        val newUserButton = findViewById<Button>(R.id.newUserButton2)

        db = DatabaseHelper.DatabaseSingleton.db

        backButton.setOnClickListener {
            finish()
        }

        newUserButton.setOnClickListener {
            Log.i("tagg", loginEdit.text.toString())
            Log.i("tagg", passwordEdit.text.toString())
            Log.i("tagg", userNameEdit.text.toString())
            insertarUsuario(db, loginEdit.text.toString(), passwordEdit.text.toString(), userNameEdit.text.toString())
            finish()
        }


    }

    fun Activity.ocultarTecladoEnToqueExterno() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    // Llamar a esta función en tu actividad para ocultar el teclado al tocar fuera de los campos de entrada
    override fun onTouchEvent(event: MotionEvent): Boolean {
        ocultarTecladoEnToqueExterno()
        return super.onTouchEvent(event)
    }

    private fun insertarUsuario(db: SQLiteDatabase, nombreUsuario: String, password: String, nombreCompleto: String) {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NOMBRE, nombreUsuario)
            put(DatabaseHelper.COLUMN_PASSWORD, password)
            put(DatabaseHelper.COLUMN_NOMBRE_COMPLETO, nombreCompleto)
            put(DatabaseHelper.COLUMN_EMAIL, "sin email")
        }

        db.insert(DatabaseHelper.TABLE_NAME, null, values)
    }
}