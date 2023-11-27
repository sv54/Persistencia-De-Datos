package com.example.pantallapreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceActivity
import android.util.Log
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class BasicSettingsActivity : PreferenceFragmentCompat() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.basic_settings, rootKey)
        val listEntries = mutableListOf<String>()
        val listValues = mutableListOf<String>()

        for (i in 1..50) {
            listEntries.add("$i")
            listValues.add("$i")
        }

        val listSize = findPreference<ListPreference>("list_preferences_size")
        listSize!!.entries = listEntries.toTypedArray()
        listSize.entryValues = listValues.toTypedArray()
        listSize.setDefaultValue("40")
    }
}