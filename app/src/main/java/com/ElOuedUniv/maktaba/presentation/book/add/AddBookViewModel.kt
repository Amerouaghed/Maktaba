package com.ElOuedUniv.maktaba.presentation.book.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<AddBookUiEvent>()
    val uiEvent: SharedFlow<AddBookUiEvent> = _uiEvent.asSharedFlow()


    fun onAction(action: AddBookUiAction) {
        when (action) {
            is AddBookUiAction.OnTitleChange -> {
                _uiState.update { it.copy(title = action.title) }
                validateInputs()

            }
            is AddBookUiAction.OnIsbnChange -> {
                _uiState.update { it.copy(isbn = action.isbn) }
                validateInputs()

            }
            is AddBookUiAction.OnPagesChange -> {
                _uiState.update { it.copy(pages = action.pages) }
                validateInputs()

            }
            is AddBookUiAction.OnConfirmClick -> {
                addBook()
            }
            is AddBookUiAction.OnImageSelected -> {
                _uiState.update { it.copy(imageUri = action.imageUri) }
            }

            else -> {}
        }
    }

    private fun validateInputs() {
        val state = _uiState.value
        val titleError = if (state.title.isBlank()) "Title cannot be empty" else null
        val isbnError = when {
            state.isbn.isBlank() -> "ISBN cannot be empty"
            state.isbn.length != 13 -> "ISBN must be exactly 13 digits"
            !state.isbn.all { it.isDigit() } -> "ISBN must contain only digits"
            else -> null
        }
        val pagesError = when {
            state.pages.isBlank ()-> "Number of pages cannot be empty"
            state.pages.toIntOrNull() == null -> "Must be a valid number"
            state.pages.toInt() <= 0 -> "Pages must be a positive number"
            else -> null
        }
        val isFormValid = titleError == null && isbnError == null && pagesError == null
        _uiState.update { it.copy(
            titleError = titleError,
            isbnError = isbnError,
            pagesError = pagesError,
            isFormValid = isFormValid
        )}
    }

    private fun addBook() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val state = _uiState.value
                val book = Book(
                    isbn = state.isbn,
                    title = state.title,
                    nbPages = state.pages.toIntOrNull()?: 0,
                    coverImage = state.imageUri
                )
                addBookUseCase(book)
                _uiEvent.emit(AddBookUiEvent.OnBookAdded)
                _uiState.update { it.copy(isSuccess = true, isLoading = false) }
            } catch (e: Exception) {
                _uiEvent.emit(AddBookUiEvent.ShowError(e.message ?: "Failed to add book"))
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
