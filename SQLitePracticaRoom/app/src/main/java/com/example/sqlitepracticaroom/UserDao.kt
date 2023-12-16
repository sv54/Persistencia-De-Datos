package com.example.sqlitepracticaroom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUsers(): List<User>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Long): User?


    @Query("SELECT * FROM user WHERE nombre_usuario = :username AND password = :password")
    fun getUser(username: String, password: String): User?

    @Insert
    fun insertUser(vararg user: User)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(user: User)
}