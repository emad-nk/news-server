package com.upday

import com.upday.datatransferobject.ArticleDTO
import com.upday.datatransferobject.AuthorDTO
import com.upday.domainobject.ArticleDO
import com.upday.domainobject.AuthorDO
import org.mockito.Mockito
import java.time.LocalDate

open class TestBase {

    fun getAuthorMocked() = Mockito.mock(AuthorDO::class.java)!!
    fun getArticleMocked() = Mockito.mock(ArticleDO::class.java)!!

    fun getArticleDTO(): ArticleDTO {
        return ArticleDTO(
        header = "some header for test",
        shortDescription = "some short description for test",
        text = "some text for test",
        keywords = arrayListOf("keyword1Test", "keyword2Test"),
        publishDate = LocalDate.now(),
        authorIds = listOf(1, 2))
    }
//
//    fun getAuthorDTO(): AuthorDTO {
//        return AuthorDTO(
//            author.firstName = "Juan"
//
//    }
//    fun getArticleDTO(): ArticleDTO {
//        val article = ArticleDTO()
//        article.header = "some header"
//        article.shortDescription = "some short description"
//        article.text = "some text"
//        article.keywords = arrayListOf("keyword1", "keyword2")
//        article.publishDate = LocalDate.now()
//        article.authorIds = listOf(1, 2)
//        return article
//    }
//
//    fun getAuthorDTO(): AuthorDTO {
//        val author = AuthorDTO()
//        author.firstName = "Juan"
//
//    }

}
