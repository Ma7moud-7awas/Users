package com.m7.users.core

fun String.isName() =
    isBlank() || !all { it.isLetter() || it.isWhitespace() }

fun String.isTitle() = isName()