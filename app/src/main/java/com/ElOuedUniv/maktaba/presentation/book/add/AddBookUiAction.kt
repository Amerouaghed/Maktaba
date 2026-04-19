package com.ElOuedUniv.maktaba.presentation.book.add

sealed class AddBookUiAction {
    data class OnTitleChange(val title: String) : AddBookUiAction()
    data class OnIsbnChange(val isbn: String) : AddBookUiAction()
    data class OnPagesChange(val pages: String) : AddBookUiAction()

    data class OnImageSelected(val imageUri: String) : AddBookUiAction()
    data object OnImageClick : AddBookUiAction()
    data object OnConfirmClick : AddBookUiAction()
}
