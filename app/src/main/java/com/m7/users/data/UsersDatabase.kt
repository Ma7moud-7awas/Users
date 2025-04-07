package com.m7.users.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.m7.users.data.model.User

@Database(version = 1, entities = [User::class], exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO

    companion object {
        private const val DB_NAME = "Users-Database"

        fun getDatabase(applicationContext: Context) =
            Room.databaseBuilder(applicationContext, UsersDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}