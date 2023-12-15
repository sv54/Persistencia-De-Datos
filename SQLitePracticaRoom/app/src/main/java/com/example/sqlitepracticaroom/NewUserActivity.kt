package com.example.sqlitepracticaroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class NewUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

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
            //insertarUsuario(db, loginEdit.text.toString(), passwordEdit.text.toString(), userNameEdit.text.toString())
            finish()
        }
    }
}