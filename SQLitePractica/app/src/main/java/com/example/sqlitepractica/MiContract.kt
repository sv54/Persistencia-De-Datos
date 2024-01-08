package com.example.sqlitepractica

import android.net.Uri

class MiContract {
    companion object {
        val CONTENT_AUTHORITY = "com.example.sqlitepractica.provider"
        val BASE_CONTENT_URI = Uri.parse("content://$CONTENT_AUTHORITY")
        val PATH_DATOS = "datos"

        val CONTENT_URI_DATOS: Uri = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DATOS).build()
        val CONTENT_URI_LOGIN: Uri = BASE_CONTENT_URI.buildUpon().appendPath("login").build()
    }
}