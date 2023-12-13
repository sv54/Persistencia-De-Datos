package com.example.sqlitepractica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitepractica.DatabaseHelper.Companion.COLUMN_NOMBRE
import com.example.sqlitepractica.DatabaseHelper.Companion.COLUMN_NOMBRE_COMPLETO
import com.example.sqlitepractica.DatabaseHelper.Companion.TABLE_NAME

class ListUsersActivity : AppCompatActivity() {

    var dataList = mutableListOf<Pair<String, String>>()
    var db = DatabaseHelper.DatabaseSingleton.db
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

        val columns = arrayOf(COLUMN_NOMBRE, COLUMN_NOMBRE_COMPLETO)

        val cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val userName = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val fullName = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE_COMPLETO))
                val pair = Pair(userName, fullName)
                dataList.add(pair)
            } while (cursor.moveToNext())
        }
    }
}