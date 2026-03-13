package com.app.cheatbite.core.domain.util

enum class ParseCallError: CallError {
    FILE_NOT_FOUND_OR_EMPTY,
    SERIALIZATION,
    UNKNOWN,
}