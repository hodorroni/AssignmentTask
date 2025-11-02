package com.rony.assignment.features.auth.login.domain

interface AuthService {
    fun register(email: String, password: String)
    fun login(email: String, password: String)
}