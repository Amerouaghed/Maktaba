package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category

/**
 * Implementation of CategoryRepository
 */
class CategoryRepositoryImpl : CategoryRepository {

    private val categoriesList = listOf(
        Category(
            id = "1",
            name = "Programming",
            description = "Books about software development and coding"
        ),
        Category(
            id = "2",
            name = "Algorithms",
            description = "Books about algorithms and data structures"
        ),
        Category(
            id = "3",
            name = "Databases",
            description = "Books about database design and management"
        ),
        Category(
            id = "4",
            name = "Web Development",
            description = "Books about HTML, CSS, JavaScript, and web frameworks"
        ),
        Category(
            id = "5",
            name = "Mobile Development",
            description = "Books about Android, iOS, and cross-platform development"
        ),
        Category(
            id = "6",
            name = "Artificial Intelligence",
            description = "Books about machine learning, AI, and data science"
        ),
        Category(
            id = "7",
            name = "Cybersecurity",
            description = "Books about network security, encryption, and ethical hacking"
        )
    )

    override fun getAllCategories(): List<Category> {
        return categoriesList
    }

    override fun getCategoryById(id: String): Category? {
        return categoriesList.find { it.id == id }
    }
}