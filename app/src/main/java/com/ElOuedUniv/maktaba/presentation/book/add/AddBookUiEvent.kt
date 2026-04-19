package com.ElOuedUniv.maktaba.presentation.book.add

sealed interface AddBookUiEvent {
    data object OnBookAdded : AddBookUiEvent
    data class ShowError(val message: String) : AddBookUiEvent
}