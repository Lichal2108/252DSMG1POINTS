package com.example.a252dsmg1points.data.model

data class User(
    val uid: String = "",
    val email: String = "",
    val fullName: String = "",
    val phone: String = "",
    val profileImageUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val isEmailVerified: Boolean = false
)
