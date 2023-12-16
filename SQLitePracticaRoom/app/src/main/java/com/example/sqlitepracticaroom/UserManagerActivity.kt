package com.example.sqlitepracticaroom

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserManagerActivity : AppCompatActivity() {
    var usuarios: MutableList<User> = mutableListOf()
    lateinit var spinner: Spinner
    lateinit var miRoomDb: AppDatabase
    lateinit var userDao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_manager)

        miRoomDb = AppDatabase.getDatabase(this)
        userDao = miRoomDb.userDao()

        actionBar?.title = "User Management"

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
            intent.putExtra("USERID", usuarios[spinner.selectedItemPosition].id)
            startActivity(intent)
        }


        deleteUserButton.setOnClickListener {
            val usuarioSeleccionado = spinner.selectedItem.toString()
            val indiceSeleccionado = spinner.selectedItemPosition

            AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Estás seguro de que deseas eliminar al usuario $usuarioSeleccionado?")
                .setPositiveButton("Eliminar") { dialog, which ->
                    CoroutineScope(Dispatchers.IO).launch {
                        userDao.deleteUser(usuarios[indiceSeleccionado])
                        usuarios.removeAt(indiceSeleccionado)
                        val nombresDeUsuarios: List<String> = usuarios.map { it.username ?: "" }
                        Log.i("tagg", usuarios.toString())

                        runOnUiThread {
                            (spinner.adapter as ArrayAdapter<*>).clear()
                            val adapter = ArrayAdapter(this@UserManagerActivity, android.R.layout.simple_spinner_item, usuarios.map { it.username ?: "" })
                            spinner.adapter = adapter
                        }
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        listUsersButton.setOnClickListener {
            intent = Intent(this, ListUserActivity::class.java)
            startActivity(intent)
        }



        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        usuarios = mutableListOf()
        CoroutineScope(Dispatchers.IO).launch {
            usuarios = userDao.getUsers().toMutableList()

            withContext(Dispatchers.Main) {
                spinner = findViewById(R.id.selectUser)
                val adapter = ArrayAdapter(this@UserManagerActivity, android.R.layout.simple_spinner_item, usuarios.map { it.username ?: "" })
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }


    }

    fun cargarUsuarios(){

        CoroutineScope(Dispatchers.IO).launch {
            usuarios = userDao.getUsers().toMutableList()
        }

    }
}