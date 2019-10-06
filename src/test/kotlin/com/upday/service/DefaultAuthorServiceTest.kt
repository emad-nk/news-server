package com.upday.service

import com.upday.TestBase
import com.upday.dataaccessobject.AuthorRepository
import com.upday.exception.EntityNotFoundException
import com.upday.service.author.DefaultAuthorService
import org.assertj.core.api.Assertions
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class DefaultAuthorServiceTest : TestBase() {

    @Mock
    private lateinit var authorRepository: AuthorRepository

    @InjectMocks
    private lateinit var service: DefaultAuthorService

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            MockitoAnnotations.initMocks(DefaultAuthorService::class.java)
        }
    }


    @Test
    fun `find should throw exception when cannot find author with id`() {
        `when`(authorRepository.findById(1)).thenReturn(Optional.empty())
        Assertions.assertThatExceptionOfType(EntityNotFoundException::class.java)
            .isThrownBy { service.getAuthorById(1) }
    }

    @Test
    fun `delete should throw exception when cannot find author with id`() {
        `when`(authorRepository.findById(1)).thenReturn(Optional.empty())
        Assertions.assertThatExceptionOfType(EntityNotFoundException::class.java)
            .isThrownBy { service.deleteAuthor(1) }
    }

    @Test
    fun `update author should throw exception when cannot find author with id`() {
        `when`(authorRepository.findById(1)).thenReturn(Optional.empty())
        Assertions.assertThatExceptionOfType(EntityNotFoundException::class.java)
            .isThrownBy { service.updateAuthor(1, getAuthorMocked()) }
    }

    @Test
    fun `verify findById is called for find`() {
        `when`(authorRepository.findById(1)).thenReturn(Optional.of(getAuthorMocked()))
        service.getAuthorById(1)
        verify(authorRepository, times(1)).findById(1)
    }

    @Test
    fun `verify findById is called for delete`() {
        `when`(authorRepository.findById(1)).thenReturn(Optional.of(getAuthorMocked()))
        service.deleteAuthor(1)
        verify(authorRepository, times(1)).findById(1)
    }

    @Test
    fun `verify save is called for creating new author`() {
        val authorDO = getAuthorMocked()
        `when`(authorRepository.save(authorDO)).thenReturn(authorDO)
        service.create(authorDO)
        verify(authorRepository, times(1)).save(authorDO)
    }

}
