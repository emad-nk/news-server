package com.upday.service.author

import com.upday.domainobject.AuthorDO

interface AuthorService {

    fun create(authorDO: AuthorDO): AuthorDO
    fun getAuthorById(authorId: Long): AuthorDO
    fun getAllAuthors(): MutableList<AuthorDO>
    fun updateAuthor(authorId: Long, authorDO: AuthorDO): AuthorDO
    fun deleteAuthor(authorId: Long)
}
