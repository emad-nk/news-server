package com.upday.service.author

import com.upday.dataaccessobject.AuthorRepository
import com.upday.domainobject.ArticleDO
import com.upday.domainobject.AuthorDO
import com.upday.exception.ConstraintsViolationException
import com.upday.exception.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DefaultAuthorService(private val authorRepository: AuthorRepository) : AuthorService {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(DefaultAuthorService::class.java)!!
    }

    /**
     * Creates a new author
     *
     * @param authorDO author domain object
     * @throws ConstraintsViolationException when cannot save the author in the DB
     * @return the created authorDO with id
     */
    override fun create(authorDO: AuthorDO): AuthorDO {
        try {
            return authorRepository.save(authorDO)
        } catch (e: DataIntegrityViolationException) {
            LOGGER.warn("ConstraintsViolationException while creating an author: $authorDO", e)
            throw ConstraintsViolationException(e.message!!)
        }
    }

    /**
     * Finds an author by its id
     *
     * @param authorId id of the author
     * @throws EntityNotFoundException when cannot find any author with provided id
     * @return found author
     */
    override fun getAuthorById(authorId: Long): AuthorDO {
        return findAuthorChecked(authorId)
    }

    /**
     * Finds all the authors
     *
     * @return list of authors
     */
    override fun getAllAuthors(): MutableList<AuthorDO> {
        return authorRepository.findAll().toMutableList()
    }

    /**
     * Updates an author firstName, lastName
     *
     * @param authorId id of the author
     * @param authorDO author domain object
     * @throws EntityNotFoundException when cannot find any author with provided id
     * @return updated authorDO
     */
    @Transactional
    override fun updateAuthor(authorId: Long, authorDO: AuthorDO): AuthorDO {
        val author = findAuthorChecked(authorId)
        author.firstName = authorDO.firstName
        author.lastName = authorDO.lastName
        return author
    }

    /**
     * Deletes an author
     *
     * @param authorId id of the author
     * @throws EntityNotFoundException when cannot find any author with provided id
     */
    @Transactional
    override fun deleteAuthor(authorId: Long) {
        authorRepository.delete(findAuthorChecked(authorId))
    }

    private fun findAuthorChecked(authorId: Long): AuthorDO {
        return authorRepository.findById(authorId)
            .orElseThrow { EntityNotFoundException("Could not find an author with id $authorId") }
    }

}
