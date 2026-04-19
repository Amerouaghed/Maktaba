package com.ElOuedUniv.maktaba.presentation.book

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ElOuedUniv.maktaba.data.model.Book
@Composable
fun AddBookDialog(
    onDismiss: () -> Unit,
    onConfirm: (Book) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }
    var nbPages by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add New Book") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = isbn,
                    onValueChange = { isbn = it },
                    label = { Text("ISBN") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = nbPages,
                    onValueChange = { nbPages = it },
                    label = { Text("Number of Pages") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && isbn.isNotBlank() && nbPages.isNotBlank()) {
                        val pages = nbPages.toIntOrNull() ?: 0
                        onConfirm(Book(isbn, title, pages))
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


