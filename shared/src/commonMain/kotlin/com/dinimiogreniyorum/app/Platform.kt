package com.dinimiogreniyorum.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform