package com.upday.service

import com.upday.domainobject.ArticleDO
import java.time.LocalDate

interface ArticleService {

    fun create(articleDO: ArticleDO, authorIds: List<Long>): ArticleDO
    fun updateArticle(articleId: Long, articleDO: ArticleDO, authorIds: List<Long>): ArticleDO
    fun getArticleById(articleId: Long): ArticleDO
    fun getArticlesFromAuthor(firstName: String, lastName: String): List<ArticleDO>
    fun getArticlesWithinPeriod(from: LocalDate, to: LocalDate): List<ArticleDO>
    fun getArticlesWithKeyword(keyword: String): List<ArticleDO>
    fun deleteArticle(articleId: Long)
}
