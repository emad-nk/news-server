package com.upday.service.article

import com.upday.domainobject.ArticleDO

interface ArticleService {

    fun create(articleDO: ArticleDO, authorIds: List<Long>): ArticleDO
    fun getArticleById(articleId: Long): ArticleDO
    fun deleteArticle(articleId: Long)
}
