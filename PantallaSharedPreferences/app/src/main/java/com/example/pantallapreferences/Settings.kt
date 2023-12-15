package com.example.pantallapreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class Settings : AppCompatActivity() {
    val drawerUtil = DrawerUtil()
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager.beginTransaction()
            .replace(R.id.content2, SettingsFragment())
            .commit()

        drawerLayout = findViewById(R.id.drawerLayout)

        supportActionBar?.title = "Settings"

        drawerUtil.setupDrawer(this)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            findPreference<Preference>("basic")?.setOnPreferenceClickListener {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.content2, BasicSettingsActivity())
                    ?.addToBackStack(null)
                    ?.commit()
                true
            }

            findPreference<Preference>("advanced")?.setOnPreferenceClickListener {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.content2, AdvancedSettingsActivity())
                    ?.addToBackStack(null)
                    ?.commit()
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return drawerUtil.onOptionsItemSelected(item, drawerLayout) || super.onOptionsItemSelected(item)
    }
}