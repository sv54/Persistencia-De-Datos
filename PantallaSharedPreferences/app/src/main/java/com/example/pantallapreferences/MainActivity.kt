package com.example.pantallapreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    lateinit var preview: Button
    lateinit var close: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Main"

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        preview = findViewById<Button>(R.id.preview)
        close = findViewById<Button>(R.id.close)

        preview.setOnClickListener {

        }

        close.setOnClickListener {
            finish()
        }

        // Configurar el ActionBarDrawerToggle
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.open_drawer, R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Configurar el icono en la barra de acción para abrir el drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Manejar clics en elementos del NavigationView
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_1 -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    // Acción para el primer elemento del menú
                    // Puedes realizar acciones o cambiar de fragmento/actividad aquí
                    true
                }
                R.id.menu_item_2 -> {
                    val intent = Intent(this, Settings::class.java)
                    startActivity(intent)

                    true
                }
                // Agrega más casos según tus necesidades

                else -> false
            }
        }
    }

    // Manejar clics en el icono de la barra de acción para abrir el drawer
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}