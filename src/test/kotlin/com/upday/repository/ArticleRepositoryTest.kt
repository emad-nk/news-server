package com.upday.repository

import com.upday.NewsApplication
import com.upday.dataaccessobject.ArticleRepository
import com.upday.dataaccessobject.AuthorRepository
import com.upday.domainobject.ArticleDO
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
}
