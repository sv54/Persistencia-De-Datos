package com.example.sqliteroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog

class UserManagerActivity : AppCompatActivity() {

    var db = DatabaseHelper.DatabaseSingleton.db
    var usuarios: MutableList<String> = mutableListOf()
    lateinit var spinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_manager)

        actionBar?.title = "User Manageement"

        val newUserButton = findViewById<Button>(R.id.newUserButton)
        val updateUserButton = findViewById<Button>(R.id.updateUserButton)
        val deleteUserButton = findViewById<Button>(R.id.deleteUserButton)
        val listUsersButton = findViewById<Button>(R.id.listUsersButton)
        val backButton = findViewById<Button>(R.id.backButton2)



        newUserButton.setOnClickListener {
            intent = Intent(this, NewUserActivity::class.java)
            startActivity(intent)
        }

        updateUserButton.setOnClickListener {
            intent = Intent(this, UpdateUserActivity::class.java)
            intent.putExtra("USERNAME", spinner.selectedItem.toString())
            startActivity(intent)
        }


        deleteUserButton.setOnClickListener {
            val usuarioSeleccionado = spinner.selectedItem.toString()

            AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Estás seguro de que deseas eliminar al usuario $usuarioSeleccionado?")
                .setPositiveButton("Eliminar") { dialog, which ->
                    val deleteQuery = "DELETE FROM ${DatabaseHelper.TABLE_NAME} WHERE ${DatabaseHelper.COLUMN_NOMBRE} = '$usuarioSeleccionado'"
                    db.execSQL(deleteQuery)
                    usuarios.remove(usuarioSeleccionado)
                    (spinner.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        listUsersButton.setOnClickListener {
            intent = Intent(this, ListUsersActivity::class.java)
            startActivity(intent)
        }



        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        usuarios = mutableListOf()
        cargarUsuarios()

        spinner = findViewById(R.id.selectUser)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, usuarios)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

    }

    fun cargarUsuarios(){
        val cursor = db.query(DatabaseHelper.TABLE_NAME, arrayOf(DatabaseHelper.COLUMN_NOMBRE),null,null,null,null,null)
        while (cursor.moveToNext()){
            val username = cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE)
            if(username != -1){
                val nombreUsuario = cursor.getString(username)
                usuarios.add(nombreUsuario)
            }
        }
    }
}