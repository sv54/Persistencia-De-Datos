package com.example.sqliteroom

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class DatabaseHelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    object DatabaseSingleton {
        lateinit var db: SQLiteDatabase
    }
    companion object {
        const val DATABASE_NAME = "midb"
        const val DATABASE_VERSION = 1

        // Define la estructura de la tabla y las consultas SQL
        const val TABLE_NAME = "Usuarios"
        const val COLUMN_ID = "ID"
        const val COLUMN_NOMBRE = "nombre_usuario"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_NOMBRE_COMPLETO = "nombre_completo"
        const val COLUMN_EMAIL = "email"

        const val CREATE_TABLE = (
                "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NOMBRE TEXT, $COLUMN_PASSWORD TEXT, $COLUMN_NOMBRE_COMPLETO TEXT, $COLUMN_EMAIL TEXT);")
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun backupDatabase(): Boolean {
        val currentDBPath = context.getDatabasePath(DATABASE_NAME).absolutePath
        val backupDBPath = File(context.getExternalFilesDir(null), DATABASE_NAME)

        try {
            val src = FileInputStream(currentDBPath).channel
            val dst = FileOutputStream(backupDBPath).channel
            dst.transferFrom(src, 0, src.size())
            src.close()
            dst.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
    }

    fun restoreDatabase(): Boolean {
        val externalFile = File(context.getExternalFilesDir(null), DATABASE_NAME)
        val internalFile = context.getDatabasePath("midb")
        Log.i("tagg", context.getExternalFilesDir(null)!!.path)
        Log.i("tagg", context.getDatabasePath("midb").path)


        try {
            val src = FileInputStream(externalFile).channel
            val dst = FileOutputStream(internalFile).channel
            dst.transferFrom(src, 0, src.size())
            src.close()
            dst.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            // Error al copiar el archivo
            return false
        }
    }

}