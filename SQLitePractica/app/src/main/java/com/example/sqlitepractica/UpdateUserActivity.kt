package com.example.sqlitepractica

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class UpdateUserActivity : AppCompatActivity() {

    val db = DatabaseHelper.DatabaseSingleton.db
    lateinit var loginEdit: EditText
    lateinit var passwordEdit: EditText
    lateinit var nombreEdit: EditText
    var id: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        loginEdit = findViewById<EditText>(R.id.loginEditUpdate)
        passwordEdit = findViewById<EditText>(R.id.passwordEditUpdate)
        nombreEdit = findViewById<EditText>(R.id.userNameEditUpdate)

        val backButton = findViewById<Button>(R.id.backButton4)
        val updateButton = findViewById<Button>(R.id.updateUserButton2)


        val userNameExtra = intent.getStringExtra("USERNAME")

        getUserData(userNameExtra!!)

        backButton.setOnClickListener {
            finish()
        }

        updateButton.setOnClickListener {
            updateUserData()
        }

    }

    fun updateUserData(){
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NOMBRE, loginEdit.text.toString())
            put(DatabaseHelper.COLUMN_PASSWORD, passwordEdit.text.toString())
            put(DatabaseHelper.COLUMN_NOMBRE_COMPLETO, nombreEdit.text.toString())
        }

        val whereClause = "${DatabaseHelper.COLUMN_ID} = ?"
        val whereArgs = arrayOf(id.toString())

        println(id.toString())

        val filasActualizadas = db.update(DatabaseHelper.TABLE_NAME, values, whereClause, whereArgs)

        if (filasActualizadas > 0) {
            Toast.makeText(this, "Update realizado con exito", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error al hacer update :(", Toast.LENGTH_SHORT).show()

            // No se pudo realizar la actualización
        }
    }

    @SuppressLint("Range")
    fun getUserData(userNameExtra: String){

        val query = "SELECT * FROM ${DatabaseHelper.TABLE_NAME} WHERE ${DatabaseHelper.COLUMN_NOMBRE} = '$userNameExtra'"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID))
            val columnIndexNombre = cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE)
            val columnIndexPassword = cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD)
            val columnIndexNombreCompleto = cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE_COMPLETO)

            val nombre = cursor.getString(columnIndexNombre)
            val password = cursor.getString(columnIndexPassword)
            val nombreCompleto = cursor.getString(columnIndexNombreCompleto)

            loginEdit.setText(nombre)
            passwordEdit.setText(password)
            nombreEdit.setText(nombreCompleto)

        } else {
            println("Error usuario no encontrado")
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


}