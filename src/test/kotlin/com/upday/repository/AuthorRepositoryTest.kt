package com.upday.repository

import com.upday.NewsApplication
import com.upday.dataaccessobject.AuthorRepository
import com.upday.domainobject.AuthorDO
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * This test class is based on the data inserted in the DB by SampleData class under util package
 */
@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [NewsApplication::class])
class AuthorRepositoryTest {

    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Test
    fun `get author by id`() {
        val author = authorRepository.findById(1)
        Assertions.assertThat(author).isPresent
        Assertions.assertThat(author.get().id).isEqualTo(1)
        Assertions.assertThat(author.get().firstName).isEqualTo("James")
    }

    @Test
    fun `get all authors`() {
        val authors = authorRepository.findAll().toMutableList()
        Assertions.assertThat(authors.size).isEqualTo(2)
        Assertions.assertThat(authors[0].firstName).isEqualTo("James")
        Assertions.assertThat(authors[1].firstName).isEqualTo("Mary")
    }

    @Test
    fun `get all authors by id`() {
        val authors = authorRepository.findAllById(listOf(1, 2)).toMutableList()
        Assertions.assertThat(authors.size).isEqualTo(2)
        Assertions.assertThat(authors[0].firstName).isEqualTo("James")
        Assertions.assertThat(authors[1].firstName).isEqualTo("Mary")
    }
}
