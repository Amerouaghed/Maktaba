package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CategoryRepositoryImpl @Inject constructor() :
    CategoryRepository {

    private val _categoriesList = listOf(

        Category(
            id = "1",
            name = "Programming",
            description = "Books about software development and coding",
            0
        ),
        Category(
            id = "2",
            name = "Algorithms",
            description = "Books about algorithms and data structures",
            0
        ),
        Category(
            id = "3",
            name = "Databases",
            description = "Books about database design and management",
            0
        ),
        Category(
            id = "4",
            name = "Web Development",
            description = "Books about HTML, CSS, JavaScript, and web frameworks",
            0
        ),
        Category(
            id = "5",
            name = "Mobile Development",
            description = "Books about Android, iOS, and cross-platform development",
            0
        ),
        Category(
            id = "6",
            name = "Artificial Intelligence",
            description = "Books about machine learning, AI, and data science",
            0
        ),
        Category(
            id = "7",
            name = "Cybersecurity",
            description = "Books about network security, encryption, and ethical hacking",
            0
        )
    )
    private val categoriesFlow = MutableSharedFlow<List<Category>>(replay = 1).apply {
        tryEmit(_categoriesList)
    }

    override fun getAllCategories(): Flow<List<Category>> = flow {
        delay(2000)
        emitAll(categoriesFlow)

    }

    override fun getCategoryById(id: String): Category? {
        return _categoriesList.find { it.id == id }
    }
}