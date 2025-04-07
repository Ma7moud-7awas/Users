package com.m7.users.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val name: String,
    val jobTitle: String,
    val age: Int,
    val gender: Gender,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
) {

    enum class Gender {
        Male,
        Female
    }
}