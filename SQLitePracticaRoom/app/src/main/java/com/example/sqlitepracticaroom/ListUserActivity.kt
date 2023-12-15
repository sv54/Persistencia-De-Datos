package com.example.sqlitepracticaroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListUserActivity : AppCompatActivity() {
    var dataList = mutableListOf<Pair<String, String>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_users)

        val backButton = findViewById<Button>(R.id.backButton5)

        val recyclerView: RecyclerView = findViewById(R.id.userList)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        cargarUsers()

        val adapter = UserListAdapter(dataList)
        recyclerView.adapter = adapter

        backButton.setOnClickListener {
            finish()
        }
    }

    fun cargarUsers(){
    }
}