package com.upday.service.search

import com.upday.domainobject.ArticleDO
import com.upday.domainobject.AuthorDO
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate
import javax.persistence.criteria.Join

object Search {

    private const val AUTHORS_FIELD = "authors"
    private const val FIRST_NAME = "firstName"
    private const val LAST_NAME = "lastName"
    private const val PUBLISH_DATE = "publishDate"
    private const val KEYWORDS = "keywords"

    fun getArticlesByAuthorFirstNameLastName(firstName: String, lastName: String): Specification<ArticleDO> {
        return getArticlesByAuthorFirstName(firstName).and(getArticleByAuthorLastName(lastName))
    }

    fun getArticlesByPeriod(from: LocalDate, to: LocalDate): Specification<ArticleDO> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.between(root.get<LocalDate>(PUBLISH_DATE), from, to)
        }
    }

    fun getArticlesByKeyword(keyword: String): Specification<ArticleDO> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.isMember(keyword, root.get(KEYWORDS))
        }
    }

    private fun getArticleByAuthorLastName(lastName: String): Specification<ArticleDO> {
        return getArticlesByAuthorJoin(LAST_NAME, lastName.trim())
    }

    private fun getArticlesByAuthorFirstName(firstName: String): Specification<ArticleDO> {
        return getArticlesByAuthorJoin(FIRST_NAME, firstName.trim())
    }

    private fun getArticlesByAuthorJoin(fieldName: String, fieldValue: String): Specification<ArticleDO> {
        return Specification { root, _, criteriaBuilder ->
            val authorJoin: Join<ArticleDO, AuthorDO> = root.join(AUTHORS_FIELD)
            criteriaBuilder.equal(authorJoin.get<String>(fieldName), fieldValue)
        }
    }
}
