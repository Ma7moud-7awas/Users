package com.m7.users.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.m7.users.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    @Query("SELECT * FROM User")
    fun getAll(): Flow<List<User>>

    @Insert
    suspend fun insert(user: User)
}