package com.example.sharedpreferences

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var seekBar: SeekBar
    lateinit var textoValores: TextView
    lateinit var edit: EditText
    lateinit var textSeekBar: TextView
    lateinit var botonAplicar: Button
    lateinit var botonJava: ImageButton


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        seekBar = findViewById<SeekBar>(R.id.seekBar)
        textoValores = findViewById<TextView>(R.id.valoresActuales)
        edit = findViewById<EditText>(R.id.editTexto)
        textSeekBar = findViewById<TextView>(R.id.valorSeekBar)
        botonAplicar = findViewById(R.id.aplicar)
        botonJava = findViewById(R.id.botonKotlin)

        seekBar.min = 1
        seekBar.max = 50
        seekBar.progress = 32
        textSeekBar.text = "Tamaño(" + seekBar.progress.toString() + "/50)"
        edit.setText("Texto por defecto")


        val pref = getSharedPreferences("misPref", Context.MODE_PRIVATE)

        if(pref.contains("FontSize")){
            seekBar.progress = decrypt(pref.getString("FontSize", encrypt("1"))).toInt()
            textSeekBar.text = "Tamaño(" + seekBar.progress.toString() + "/50)"
            edit.setText(decrypt(pref.getString("Texto", encrypt("null"))))
        }

        updateTextValores()

        edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateTextValores()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Actualizar el EditText con el valor actual del SeekBar
                textSeekBar.text = "Tamaño(" + progress.toString() + "/50)"
                updateTextValores()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })



        botonAplicar.setOnClickListener {
            val editor = pref.edit()
            editor.putString("FontSize", encrypt(seekBar.progress.toString()))
            editor.putString("Texto", encrypt(edit.text.toString()))
            editor.apply()

            val intent = Intent(this, KotlinMostrarResultado::class.java)
            startActivity(intent)

        }

        botonJava.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivityJava::class.java)
            startActivity(intent)
            finish()

        }

        val botonSalir = findViewById<Button>(R.id.botonSalir)
        botonSalir.setOnClickListener {
            finish()
        }



    }


    fun updateTextValores(){
        textoValores.text = "Valores actuales de la aplicacion:\n" +
                "Texto: " + edit.text + "\n" + "Tamaño: " + seekBar.progress
    }

    fun encrypt(input: String?): String {
        return Base64.encodeToString(input?.toByteArray(), Base64.DEFAULT)
    }
    fun decrypt(input: String?): String {
        return String(Base64.decode(input?.toByteArray(), Base64.DEFAULT))
    }

}