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
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var closeButton: Button
    lateinit var loginButton: Button
    lateinit var editUsername: EditText
    lateinit var editPassword: EditText
    var checkExternalStorage = false
    lateinit var miRoomDb: AppDatabase
    lateinit var userDao: UserDao

    val DATABASE_NAME = "miRoomdb"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        closeButton = findViewById(R.id.close)
        loginButton = findViewById(R.id.login)
        editUsername = findViewById(R.id.editUsername)
        editPassword = findViewById(R.id.editPassword)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "SQLite"

        miRoomDb = AppDatabase.getDatabase(this)
        userDao = miRoomDb.userDao()

        closeButton.setOnClickListener {
            finish()
            finishAffinity()
        }

        loginButton.setOnClickListener {
            var response: User? = null

            CoroutineScope(Dispatchers.IO).launch {
                response =
                    userDao.getUser(editUsername.text.toString(), editPassword.text.toString())

                withContext(Dispatchers.Main) {
                    if (response != null) {
                        intent = Intent(this@MainActivity, UserDataActivity::class.java)
                        intent.putExtra("USERNAME", response!!.username)
                        intent.putExtra("NOMBRECOMPLETO", response!!.fullname)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Usuario o contraseña incorrectos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


    }

    fun login(){

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
                    if (backupDatabase()) {
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

                    if (restoreDatabase()) {

                        Toast.makeText(this, "Restore exitoso", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error al realizar el restore", Toast.LENGTH_SHORT).show()
                    }
                }
                return true
            }
            R.id.action_ManageUsers -> {
//                insertarUsuario(db, "AntesDeRestore", "123456", "Nombre Completo", "usuario@dominio.com")
                intent = Intent(this, UserManagerActivity::class.java)
                startActivity(intent)
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
                val uri = Uri.parse("package:" + "com.example.sqlitepracticaroom")
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
    fun backupDatabase(): Boolean {
        val currentDBPath = this.getDatabasePath(DATABASE_NAME).absolutePath
        val backupDBPath = File(this.getExternalFilesDir(null), DATABASE_NAME)

        try {
            val src = FileInputStream(currentDBPath).channel
            val dst = FileOutputStream(backupDBPath).channel
            dst.transferFrom(src, 0, src.size())
            src.close()
            dst.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
    }

    fun restoreDatabase(): Boolean {
        val externalFile = File(this.getExternalFilesDir(null), DATABASE_NAME)
        val internalFile = this.getDatabasePath(DATABASE_NAME)
        Log.i("tagg", this.getExternalFilesDir(null)!!.path)
        Log.i("tagg", this.getDatabasePath(DATABASE_NAME).path)


        try {
            val src = FileInputStream(externalFile).channel
            val dst = FileOutputStream(internalFile).channel
            dst.transferFrom(src, 0, src.size())
            src.close()
            dst.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            // Error al copiar el archivo
            return false
        }
    }
}