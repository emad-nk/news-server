package com.upday.service.article

import com.upday.domainobject.ArticleDO
import java.time.LocalDate

interface ArticleService {

    fun create(articleDO: ArticleDO, authorIds: List<Long>): ArticleDO
    fun getArticleById(articleId: Long): ArticleDO
    fun getArticlesFromAuthor(firstName: String, lastName: String): List<ArticleDO>
    fun getArticlesWithinPeriod(from: LocalDate, to: LocalDate): List<ArticleDO>
    fun deleteArticle(articleId: Long)
}
