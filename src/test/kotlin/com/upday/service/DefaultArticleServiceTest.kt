package com.upday.service

import com.upday.TestBase
import com.upday.dataaccessobject.ArticleRepository
import com.upday.dataaccessobject.AuthorRepository
import com.upday.exception.EntityNotFoundException
import com.upday.service.article.DefaultArticleService
import org.assertj.core.api.Assertions
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class DefaultArticleServiceTest : TestBase() {

    @Mock
    private lateinit var articleRepository: ArticleRepository

    @Mock
    private lateinit var authorRepository: AuthorRepository

    @InjectMocks
    private lateinit var service: DefaultArticleService

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            MockitoAnnotations.initMocks(DefaultArticleService::class.java)
        }
    }

    @Test
    fun `find should throw exception when cannot find article with id`() {
        `when`(articleRepository.findById(1)).thenReturn(Optional.empty())
        Assertions.assertThatExceptionOfType(EntityNotFoundException::class.java)
            .isThrownBy { service.getArticleById(1) }
    }

    @Test
    fun `create should throw exception if it cannot find all the authors`() {
        val articleDO = getArticleMocked()
        `when`(articleRepository.save(articleDO)).thenReturn(articleDO)
        `when`(authorRepository.findAllById(listOf(1, 2))).thenReturn(listOf(getAuthorMocked()))
        Assertions.assertThatExceptionOfType(EntityNotFoundException::class.java)
            .isThrownBy { service.create(articleDO, listOf(1, 2)) }
    }

    @Test
    fun `delete should throw exception when cannot find article with id`() {
        `when`(articleRepository.findById(1)).thenReturn(Optional.empty())
        Assertions.assertThatExceptionOfType(EntityNotFoundException::class.java)
            .isThrownBy { service.deleteArticle(1) }
    }

    @Test
    fun `update should throw exception when cannot find article with id`() {
        val articleDO = getArticleMocked()
        `when`(articleRepository.findById(1)).thenReturn(Optional.of(articleDO))
        `when`(authorRepository.findAllById(listOf(1, 2))).thenReturn(emptyList())
        Assertions.assertThatExceptionOfType(EntityNotFoundException::class.java)
            .isThrownBy { service.updateArticle(1, articleDO, listOf(1, 2)) }
    }

    @Test
    fun `verify findById is called for find`() {
        `when`(articleRepository.findById(1)).thenReturn(Optional.of(getArticleMocked()))
        service.getArticleById(1)
        verify(articleRepository, Mockito.times(1)).findById(1)
    }

    @Test
    fun `verify findById is called for delete`() {
        `when`(articleRepository.findById(1)).thenReturn(Optional.of(getArticleMocked()))
        service.deleteArticle(1)
        verify(articleRepository, Mockito.times(1)).findById(1)
    }

    @Test
    fun `verify save is called for creating new article`() {
        val articleDO = getArticleMocked()
        `when`(articleRepository.save(articleDO)).thenReturn(articleDO)
        `when`(authorRepository.findAllById(listOf(1))).thenReturn(listOf(getAuthorMocked()))
        service.create(articleDO, listOf(1))
        verify(articleRepository, Mockito.times(1)).save(articleDO)
    }

}
