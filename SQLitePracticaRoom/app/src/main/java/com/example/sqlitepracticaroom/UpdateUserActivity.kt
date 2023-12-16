package com.example.sqlitepracticaroom

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class UpdateUserActivity : AppCompatActivity() {
    lateinit var loginEdit: EditText
    lateinit var passwordEdit: EditText
    lateinit var nombreEdit: EditText
    var id: Int = -1

    lateinit var miRoomDb: AppDatabase
    lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        miRoomDb = AppDatabase.getDatabase(this)
        userDao = miRoomDb.userDao()

        actionBar?.title = "Update User"

        loginEdit = findViewById<EditText>(R.id.loginEditUpdate)
        passwordEdit = findViewById<EditText>(R.id.passwordEditUpdate)
        nombreEdit = findViewById<EditText>(R.id.userNameEditUpdate)

        val backButton = findViewById<Button>(R.id.backButton4)
        val updateButton = findViewById<Button>(R.id.updateUserButton2)


        val userIdExtra = intent.getLongExtra("USERID", -1)

        var usuario: User? = null

        CoroutineScope(Dispatchers.IO).launch {
            usuario = userDao.getUserById(userIdExtra)

            withContext(Dispatchers.Main) {
                loginEdit.setText(usuario!!.username)
                passwordEdit.setText(usuario!!.password)
                nombreEdit.setText(usuario!!.fullname)
            }
        }



        backButton.setOnClickListener {
            finish()
        }

        updateButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                userDao.updateUser(User(userIdExtra, loginEdit.text.toString(), passwordEdit.text.toString(), nombreEdit.text.toString(), "email"))
            }
            finish()
        }

    }

    @SuppressLint("Range")
    fun getUserData(userNameExtra: String){
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