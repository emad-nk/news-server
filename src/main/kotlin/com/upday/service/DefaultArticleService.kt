package com.upday.service

import com.upday.dataaccessobject.ArticleRepository
import com.upday.dataaccessobject.AuthorRepository
import com.upday.domainobject.ArticleDO
import com.upday.domainobject.AuthorDO
import com.upday.exception.ConstraintsViolationException
import com.upday.exception.EntityNotFoundException
import com.upday.service.search.Search
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.time.LocalDate
import javax.transaction.Transactional

@Service
class DefaultArticleService(private val articleRepository: ArticleRepository,
                            private val authorRepository: AuthorRepository) : ArticleService {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(DefaultArticleService::class.java)!!
    }

    /**
     * Creates a new article
     *
     * @param articleDO article domain object
     * @param authorIds list of author IDs
     * @throws EntityNotFoundException when cannot find all the IDs
     * @throws ConstraintsViolationException when cannot save the object in the DB
     * @return the created articleDO with ID
     */
    override fun create(articleDO: ArticleDO, authorIds: List<Long>): ArticleDO {
        articleDO.authors = findAuthorsChecked(authorIds)
        try {
            return articleRepository.save(articleDO)
        } catch (e: DataIntegrityViolationException) {
            LOGGER.warn("ConstraintsViolationException while creating an author: $articleDO", e)
            throw ConstraintsViolationException(e.message!!)
        }
    }

    /**
     * Updates an article
     *
     * @param articleId id of the article that needs to be updated
     * @param articleDO articleDO update object
     * @param authorIds list of author ids
     * @throws EntityNotFoundException when cannot find all the IDs
     * @return the updated articleDO
     */
    @Transactional
    override fun updateArticle(articleId: Long, articleDO: ArticleDO, authorIds: List<Long>): ArticleDO {
        val article = findArticleChecked(articleId)
        article.authors = findAuthorsChecked(authorIds)
        article.header = articleDO.header
        article.text = articleDO.text
        article.shortDescription = articleDO.shortDescription
        article.keywords = articleDO.keywords
        article.publishDate = articleDO.publishDate
        return article
    }

    /**
     * Gets an article by its ID
     *
     * @param articleId ID of article
     * @throws EntityNotFoundException when cannot find an article with provided ID
     * @return the articleDO which is found
     */
    override fun getArticleById(articleId: Long): ArticleDO {
        return findArticleChecked(articleId)
    }

    /**
     * Gets list of articles for a specific author
     *
     * @param firstName first name of the author
     * @param lastName last name of the author
     * @return list of articleDO which is found
     */
    override fun getArticlesFromAuthor(firstName: String, lastName: String): List<ArticleDO> {
        return articleRepository.findAll(Search.getArticlesByAuthorFirstNameLastName(firstName, lastName))
    }

    /**
     * Gets list of articles within specified dates
     *
     * @param from from date
     * @param to to date
     * @return list of articleDO which is found
     */
    override fun getArticlesWithinPeriod(from: LocalDate, to: LocalDate): List<ArticleDO> {
        return articleRepository.findAll(Search.getArticlesByPeriod(from, to))
    }

    /**
     * Gets list of articles with specified keyword
     *
     * @param keyword to search for in articles
     * @return list of articleDO which is found
     */
    override fun getArticlesWithKeyword(keyword: String): List<ArticleDO> {
        return articleRepository.findAll(Search.getArticlesByKeyword(keyword))
    }

    /**
     * Deletes an article
     *
     * @param articleId ID of article
     * @throws EntityNotFoundException when cannot find all the IDs
     */
    @Transactional
    override fun deleteArticle(articleId: Long) {
        val article = findArticleChecked(articleId)
        article.authors.removeAll(article.authors)
        articleRepository.delete(article)
    }

    private fun findArticleChecked(id: Long): ArticleDO {
        return articleRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Could not find an article with id $id") }
    }

    private fun findAuthorsChecked(ids: List<Long>): MutableList<AuthorDO> {
        val authors = authorRepository.findAllById(ids).toMutableList()
        if (authors.size != ids.size) {
            throw EntityNotFoundException("Could not find provided ID(s): $ids")
        }
        return authors
    }

}
