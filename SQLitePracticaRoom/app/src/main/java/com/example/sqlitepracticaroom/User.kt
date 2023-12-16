package com.example.sqlitepracticaroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(true)@ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "nombre_usuario") val username: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "nombre_completo") val fullname: String?,
    @ColumnInfo(name = "email") val email: String?

)
