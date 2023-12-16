package com.example.sqlitepracticaroom

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewUserActivity : AppCompatActivity() {
    lateinit var miRoomDb: AppDatabase
    lateinit var userDao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        miRoomDb = AppDatabase.getDatabase(this)
        userDao = miRoomDb.userDao()

        actionBar?.title = "New User"

        val loginEdit = findViewById<EditText>(R.id.loginEdit)
        val passwordEdit = findViewById<EditText>(R.id.passwordEdit)
        val userNameEdit = findViewById<EditText>(R.id.userNameEdit)

        val backButton = findViewById<Button>(R.id.backButton3)
        val newUserButton = findViewById<Button>(R.id.newUserButton2)


        backButton.setOnClickListener {
            finish()
        }

        newUserButton.setOnClickListener {
            Log.i("tagg", loginEdit.text.toString())
            Log.i("tagg", passwordEdit.text.toString())
            Log.i("tagg", userNameEdit.text.toString())
            val newUser = User(0,loginEdit.text.toString(), passwordEdit.text.toString(), userNameEdit.text.toString() ,"email")
            CoroutineScope(Dispatchers.IO).launch {
                userDao.insertUser(newUser)
            }
            //insertarUsuario(db, loginEdit.text.toString(), passwordEdit.text.toString(), userNameEdit.text.toString())
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        ocultarTecladoEnToqueExterno()
        return super.onTouchEvent(event)
    }

}