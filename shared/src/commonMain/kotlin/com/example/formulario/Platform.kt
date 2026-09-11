package com.example.formulario

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform