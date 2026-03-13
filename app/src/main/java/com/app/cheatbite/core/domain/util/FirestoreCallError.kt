package com.app.cheatbite.core.domain.util

enum class FirestoreCallError : CallError {
    PERMISSION_DENIED,
    DOCUMENT_NOT_FOUND,
    WRITE_FAILED,
    SERIALIZATION,
    UNAUTHENTICATED,
    UNKNOWN
}