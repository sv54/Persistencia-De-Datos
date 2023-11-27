package com.example.pantallapreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout

class Settings : AppCompatActivity() {
    val drawerUtil = DrawerUtil()
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        drawerLayout = findViewById(R.id.drawerLayout)

        supportActionBar?.title = "Settings"

        drawerUtil.setupDrawer(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return drawerUtil.onOptionsItemSelected(item, drawerLayout) || super.onOptionsItemSelected(item)
    }
}