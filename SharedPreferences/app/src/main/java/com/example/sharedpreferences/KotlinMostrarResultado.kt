package com.example.sharedpreferences

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.TextView

class KotlinMostrarResultado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_mostrar_resultado)

        var botonVolver = findViewById<Button>(R.id.BotonVolver)
        var texto = findViewById<TextView>(R.id.textoMostrar)

        val misPrefs = getSharedPreferences("misPref", Context.MODE_PRIVATE)

        texto.textSize = decrypt(misPrefs.getString("FontSize", encrypt("50"))).toFloat()
        texto.text = decrypt(misPrefs.getString("Texto", encrypt("null")))




        botonVolver.setOnClickListener {
            finish()
        }
    }

    fun encrypt(input: String?): String {
        return Base64.encodeToString(input?.toByteArray(), Base64.DEFAULT)
    }
    fun decrypt(input: String?): String {
        return String(Base64.decode(input?.toByteArray(), Base64.DEFAULT))
    }

}