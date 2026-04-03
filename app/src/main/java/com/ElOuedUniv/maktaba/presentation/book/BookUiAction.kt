package com.ElOuedUniv.maktaba.presentation.book

import com.ElOuedUniv.maktaba.data.model.Book

/**
 * UI Actions representing user interactions on the Book screen.
 * TODO: Student must implement and use these actions in the ViewModel.
 */
sealed interface BookUiAction {
    data object RefreshBooks : BookUiAction
    data object OnAddBookClick : BookUiAction
    data object OnDismissAddBook : BookUiAction
    data class OnAddBookConfirm(val book: Book) : BookUiAction
    data class OnSearchQueryChange(val query: String) : BookUiAction
    data class OnBookClick(val bookId: String) : BookUiAction

    annotation class OnRefreshBooks
}