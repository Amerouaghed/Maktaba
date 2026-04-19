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
        Book("978-0-134-685-991", "Clean Code", 464, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQR_lg6A48X1Ql_ykCGRvfm7A4DXNpzsjdI_A&s"),
        Book("978-0-262-033-848", "Introduction to Algorithms", 1312, "https://covers.openlibrary.org/b/isbn/9780262033848-M.jpg"),
        Book("978-0-201-633-610", "Design Patterns", 395, "https://covers.openlibrary.org/b/isbn/9780201633610-M.jpg"),
        Book("978-1-491-947-284", "Kotlin in Action", 360, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTv9peOM44ra484vWOMpge5baLaS9kyRXGacw&s"),
        Book("978-1-593-275-990", "Automate the Boring Stuff with Python", 592, "https://covers.openlibrary.org/b/isbn/9781593275990-M.jpg"),
        Book("978-0-321-356-680", "Effective Java", 416, "https://covers.openlibrary.org/b/isbn/9780321356680-M.jpg"),
        Book("978-1-491-924-285", "Android Programming", 812, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMoUhi2ReKwop_jslgggR0DscDDdx3YNDLYA&s"),
        Book("978-0-134-494-166", "Clean Architecture", 432, "https://covers.openlibrary.org/b/isbn/9780134494166-M.jpg"),
        Book("978-0-201-616-224", "The Pragmatic Programmer", 352, "https://covers.openlibrary.org/b/isbn/9780201616224-M.jpg"),
        Book("978-0-201-485-677", "Refactoring", 464, "https://covers.openlibrary.org/b/isbn/9780201485677-M.jpg")
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
