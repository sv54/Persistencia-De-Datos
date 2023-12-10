package com.example.sqlitepractica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class UserDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)

        actionBar?.title = "User Data"

        val nombreCompletoText = findViewById<TextView>(R.id.nombreCompletoText)
        val usernameText = findViewById<TextView>(R.id.userNameText)
        val backButton = findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }

        val nombreCompletoExtra = intent.getStringExtra("NOMBRECOMPLETO")
        val userNameExtra = intent.getStringExtra("USERNAME")

        nombreCompletoText.text = nombreCompletoExtra
        usernameText.text = userNameExtra


    }
}