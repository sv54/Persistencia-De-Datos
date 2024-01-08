package com.example.sqlitepractica

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.util.Log

class MiContentProvider: ContentProvider() {

    private val PROVIDER_NAME = "com.example.sqlitepractica.provider"
    lateinit var db: SQLiteDatabase

    companion object {
        private const val URI_DATOS = 1
        private const val URI_LOGIN = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI("com.example.sqlitepractica.provider", "datos", URI_DATOS)
            addURI("com.example.sqlitepractica.provider", "login", URI_LOGIN)
        }
    }

    override fun onCreate(): Boolean {
        val dbHelper = DatabaseHelper(context!!)

        db = dbHelper.writableDatabase
        DatabaseHelper.DatabaseSingleton.db = db

        return true
    }



    fun login(username: String, password: String): Bundle? {
        val selectUserQuery = "${DatabaseHelper.COLUMN_NOMBRE}=? AND ${DatabaseHelper.COLUMN_PASSWORD}=?"
        val selectionArgs = arrayOf(username, password)

        val cursor = query(MiContract.CONTENT_URI_LOGIN, null, selectUserQuery, selectionArgs, null)

        try {
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndexFullName = cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE_COMPLETO)
                if (columnIndexFullName != -1) {
                    Log.i("tagg", "Login try")

                    val result = Bundle()
                    result.putString("NOMBRECOMPLETO", cursor.getString(columnIndexFullName))
                    result.putString("USERNAME", username)
                    Log.i("tagg", result.toString())

                    return result
                }
            }
        } finally {
            cursor?.close()
        }

        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.i("tagg", "query")
        val match = uriMatcher.match(uri)
        return when (match) {
            URI_DATOS -> {
                // Lógica de consulta para URI_DATOS
                val cursor = db.query(
                    DatabaseHelper.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                )
                cursor.setNotificationUri(context?.contentResolver, uri)
                cursor
            }
            else -> throw IllegalArgumentException("URI desconocida: $uri")
        }

    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null

    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0

    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

}