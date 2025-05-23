package com.example.contectmanagerapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import androidx.lifecycle.LiveData
import com.example.contectmanagerapp.room.User

@Dao
interface UserDAO {

    @Insert
    suspend fun insertUser(user: User)  : Long //is a keyweord that tells Kotlin that this function can be suspended and can be called from a coroutine.


    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

   @Query("DELETE FROM user")
    suspend fun deleteAll()

    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<User>> //we did not use suspend because it will be on the background we do not need to show this



}