package com.example.pantallapreferences

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var inputMethodManager: InputMethodManager
    lateinit var preview: Button
    lateinit var close: Button
    lateinit var editText: EditText
    val drawerUtil = DrawerUtil()
    lateinit var prefs: SharedPreferences
    lateinit var mostrar: TextView


    override fun onResume() {
        super.onResume()
        mostrar.visibility = View.INVISIBLE

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mostrar = findViewById<TextView>(R.id.mostrar)



        supportActionBar?.title = "Main"

        drawerLayout = findViewById(R.id.drawerLayout)
        inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        preview = findViewById<Button>(R.id.preview)
        close = findViewById<Button>(R.id.close)
        editText = findViewById(R.id.writeSomething)
        editText.setText("Texto por defecto")
        mostrar.visibility = View.INVISIBLE

        preview.setOnClickListener {
            hideKeyboard()


            prefs = PreferenceManager.getDefaultSharedPreferences(this)
            val bgColor = prefs.getString("bg_color", "white")
            val fbColor = prefs.getString("fb_color", "black")
            val fontSize = prefs.getString("list_preferences_size", "24.0")
            val bold = prefs.getBoolean("switch_preference_bold", false)
            val italic = prefs.getBoolean("switch_preference_italic", false)
            val rotacion = prefs.getInt("rotation_preference", 0)
            val alpha = prefs.getInt("alpha_preference", 100)
            mostrar.setBackgroundColor(resources.getColor(resources.getIdentifier(bgColor, "color", packageName)))
            mostrar.setTextColor(resources.getColor(resources.getIdentifier(fbColor, "color", packageName)))
            mostrar.text = editText.text.toString()
            mostrar.textSize = fontSize!!.toFloat()

            if (bold && italic){
                mostrar.setTypeface(null, Typeface.BOLD_ITALIC)
            }
            else if(bold){
                mostrar.setTypeface(null, Typeface.BOLD)
            }
            else if(italic){
                mostrar.setTypeface(null, Typeface.ITALIC)
            }
            else{
                mostrar.setTypeface(null, Typeface.NORMAL)
            }

            //rotacion
            mostrar.rotation = rotacion.toFloat()
            mostrar.alpha = (alpha.toFloat()/100)

            mostrar.visibility = View.VISIBLE

        }

        close.setOnClickListener {
            finishAffinity()
        }

        // Configurar el ActionBarDrawerToggle
        drawerUtil.setupDrawer(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return drawerUtil.onOptionsItemSelected(item, drawerLayout) || super.onOptionsItemSelected(item)
    }

    private fun hideKeyboard() {
        val currentFocusView: View? = currentFocus
        currentFocusView?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}