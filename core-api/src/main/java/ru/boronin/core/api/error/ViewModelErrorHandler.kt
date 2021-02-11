package ru.boronin.core.api.error

interface ViewModelErrorHandler {
  fun forceLogout()
  fun errorToast(text: String)
  fun errorMessage(text: String)
}
