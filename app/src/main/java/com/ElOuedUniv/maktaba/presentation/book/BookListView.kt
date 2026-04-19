package com.ElOuedUniv.maktaba.presentation.book

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ElOuedUniv.maktaba.data.model.Book



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListView(

            onCategoriesClick: () -> Unit = {},
            onAddBookClick: () -> Unit = {},
            onBookClick: (Book) -> Unit = {},
            viewModel: BookViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is BookUiEvent.ShowToast -> println("Toast: ${event.message}")
                is BookUiEvent.ShowError -> println("Error: ${event.message}")
                is BookUiEvent.NavigateToDetails -> println("Navigate to: ${event.bookId}")
                is BookUiEvent.NavigateToCategories -> onCategoriesClick()
                is BookUiEvent.ShowSnackbar -> println("Snackbar: ${event.message}")
            }
        }
    }

    // TODO: Exercise 3 - Use a single delegated state from the ViewModel
    // val uiState by viewModel.uiState.collectAsState()


    if (uiState.isAddingBook) {
        AddBookDialog(
            onDismiss = { viewModel.onAction(BookUiAction.OnDismissAddBook) },
            onConfirm = { book ->
                viewModel.onAction(BookUiAction.OnAddBookConfirm(book))
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📚  My Library") },
                actions = {
                    IconButton(onClick = onCategoriesClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Categories")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {

            FloatingActionButton(

                onClick = onAddBookClick) {

                Icon(Icons.Default.Add, contentDescription = "Add Book")

            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                if (uiState.books.isEmpty()) {
                    EmptyBooksMessage(
                        modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.books) { book ->
                            BookCard(
                                book = book,
                                onClick = { onBookClick(book) }
                    )
                }
            }
        }
    }
}
    }}      

@Composable
fun BookCard(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                   .height(280.dp)
                   .clickable { onClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                val imageUri = book.coverImage ?: book.imageUrl

                if (!imageUri.isNullOrEmpty()) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = book.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("📖", style = MaterialTheme.typography.displayMedium)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "ISBN: ${book.isbn.take(10)}...",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = "📖 Reading",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp ))
            }
        }
}
}

@Composable
fun EmptyBooksMessage(modifier: Modifier = Modifier){
Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("📚", style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("No books in your library", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Click the + button to add a book", style = MaterialTheme.typography.bodyMedium)
}
}
