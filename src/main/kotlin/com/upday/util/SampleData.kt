package com.upday.util

import com.upday.dataaccessobject.ArticleRepository
import com.upday.dataaccessobject.AuthorRepository
import com.upday.domainobject.ArticleDO
import com.upday.domainobject.AuthorDO
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@Profile("dev")
class SampleData(var authorRepository: AuthorRepository, val articleRepository: ArticleRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val author1 = AuthorDO(id = 1, firstName = "James", lastName = "Henry")
        val author2 = AuthorDO(id = 2, firstName = "Mary", lastName = "Writer")

        authorRepository.save(author1)
        authorRepository.save(author2)

        val article1 = ArticleDO(id = 1,
            header = "some header",
            shortDescription = "some short description",
            text = "some text",
            keywords = arrayListOf("planet", "keyword2"),
            publishDate = LocalDate.now(),
            authors = mutableListOf(author1))

        val article2 = ArticleDO(id = 2,
            header = "some header2",
            shortDescription = "some short description2",
            text = "some text2",
            keywords = arrayListOf("planet", "keyword51"),
            publishDate = LocalDate.now(),
            authors = mutableListOf(author1, author2))

        article1.authors = mutableListOf(author1)
        article2.authors = mutableListOf(author1, author2)

        articleRepository.save(article1)
        articleRepository.save(article2)
    }
}
