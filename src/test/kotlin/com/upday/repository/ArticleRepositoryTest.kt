package com.upday.repository

import com.upday.NewsApplication
import com.upday.dataaccessobject.ArticleRepository
import com.upday.service.search.Search
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.time.LocalDate

/**
 * This test class is based on the data inserted in the DB by SampleData class under util package
 */
@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [NewsApplication::class])
class ArticleRepositoryTest {

    @Autowired
    private lateinit var articleRepository: ArticleRepository

    @Test
    fun `get article by id`() {
        val article = articleRepository.findById(1)
        Assertions.assertThat(article).isPresent
        Assertions.assertThat(article.get().id).isEqualTo(1)
        Assertions.assertThat(article.get().header).isEqualTo("some header")
    }

    @Test
    fun `get articles by author name`(){
        val articles = articleRepository.findAll(Search.getArticlesByAuthorFirstNameLastName("James", "Henry"))
        Assertions.assertThat(articles).hasSize(2)
        Assertions.assertThat(articles[0].header).isEqualTo("some header")
        Assertions.assertThat(articles[1].header).isEqualTo("some header2")
    }

    @Test
    fun `get articles by author name that do not exist`(){
        val articles = articleRepository.findAll(Search.getArticlesByAuthorFirstNameLastName("DoNotExist", "Henry"))
        Assertions.assertThat(articles).hasSize(0)
    }

    @Test
    fun `get articles within specified dates`(){
        val articles = articleRepository.findAll(Search.getArticlesByPeriod(LocalDate.now().minusDays(1),
            LocalDate.now()))
        Assertions.assertThat(articles).hasSize(2)
        Assertions.assertThat(articles[0].header).isEqualTo("some header")
        Assertions.assertThat(articles[1].header).isEqualTo("some header2")
    }

    @Test
    fun `get articles within specified dates should not find anything`(){
        val articles = articleRepository.findAll(Search.getArticlesByPeriod(LocalDate.now().minusDays(10),
            LocalDate.now().minusDays(1)))
        Assertions.assertThat(articles).hasSize(0)
    }
}
