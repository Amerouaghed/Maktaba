package com.ElOuedUniv.maktaba.presentation.book.detail

sealed class BookDetailUiAction {
    annotation class OnRetryClick
    annotation class OnRefreshClick

    object OnBackClick : BookDetailUiAction()
}
