package dev.haas.learningman

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform