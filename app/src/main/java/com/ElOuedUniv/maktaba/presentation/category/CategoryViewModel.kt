package com.ElOuedUniv.maktaba.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Category
import com.ElOuedUniv.maktaba.domain.usecase.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<CategoryUiEvent>()
    val uiEvent: SharedFlow<CategoryUiEvent> = _uiEvent.asSharedFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {

                getCategoriesUseCase().collect { categoryList ->
                    _uiState.update { it.copy(
                        categories = categoryList,
                        isLoading = false
                    )}
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to load categories"
                )}
                _uiEvent.emit(CategoryUiEvent.ShowError(e.message ?: "Failed to load categories"))
            }
        }
    }

    fun onAction(action: CategoryUiAction) {
        when (action) {
            is CategoryUiAction.OnRefreshClick -> {
                loadCategories()
            }
            is CategoryUiAction.OnCategoryClick -> {
                viewModelScope.launch {
                    _uiEvent.emit(CategoryUiEvent.NavigateToCategory(action.categoryId))
                }
            }


            else -> {}
        }
    }
}