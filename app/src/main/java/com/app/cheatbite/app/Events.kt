package com.app.cheatbite.app

import com.app.cheatbite.core.domain.util.CallError

sealed interface Events {
    data class Success(val message: String?): Events
    data class Error(val error: CallError,val message: String?): Events
    data class LoggedOut(val message: String?): Events
}
