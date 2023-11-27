package com.example.pantallapreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var inputMethodManager: InputMethodManager
    lateinit var preview: Button
    lateinit var close: Button
    val drawerUtil = DrawerUtil()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Main"

        drawerLayout = findViewById(R.id.drawerLayout)
        inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        preview = findViewById<Button>(R.id.preview)
        close = findViewById<Button>(R.id.close)

        preview.setOnClickListener {
            hideKeyboard()
        }

        close.setOnClickListener {
            finish()
        }

        // Configurar el ActionBarDrawerToggle
        drawerUtil.setupDrawer(this)
    }

    // Manejar clics en el icono de la barra de acción para abrir el drawer
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return drawerUtil.onOptionsItemSelected(item, drawerLayout) || super.onOptionsItemSelected(item)
    }


    //MOVERLO A DRAWEEUTIL
    private fun hideKeyboard() {
        val currentFocusView: View? = currentFocus
        currentFocusView?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}