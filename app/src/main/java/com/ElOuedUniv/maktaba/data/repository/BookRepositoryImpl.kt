package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor() : BookRepository {


    private val _booksList =mutableListOf(
        Book("978-0-13-468599-1", "Clean Code", 464),
        Book("978-0-596-52068-7", "Head First Design Patterns", 694),
        Book("978-0-201-63361-0", "Design Patterns", 395),
        Book("978-1-491-94728-4", "Kotlin in Action", 360),
        Book("978-1-59327-599-0", "Automate the Boring Stuff with Python", 592),

        Book("978-0-321-35668-0", "Effective Java", 416),
        Book("978-1-491-92428-5", "Android Programming", 812),
        Book("978-0-13-235088-4", "Clean Architecture", 432),
        Book("978-1-118-96914-3", "Android Development for Beginners", 720),
        Book("978-1491950357", "Designing Data-Intensive Applications", 616)
    )
    private val booksFlow = MutableSharedFlow<List<Book>>(replay = 1)

    init {
        booksFlow.tryEmit(_booksList.toList())
    }

    override fun getAllBooks(): Flow<List<Book>> = flow {
        delay(2000) // Simulate delay
        booksFlow.collect { books ->
            emit(books)
        }
    }

    override suspend fun getBookByIsbn(isbn: String): Book? {
        return _booksList.find { it.isbn == isbn }
    }

    override suspend fun addBook(book: Book) {
        if (_booksList.any { it.isbn == book.isbn }) {
            throw IllegalArgumentException("Book with ISBN ${book.isbn} already exists")
        }


        _booksList.add(book)
        booksFlow.emit(_booksList.toList())
    }
}
