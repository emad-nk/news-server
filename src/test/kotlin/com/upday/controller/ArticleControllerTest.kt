package com.upday.controller

import com.upday.TestBase
import com.upday.datatransferobject.ArticleDTO
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate
import java.util.HashMap
import org.springframework.http.HttpEntity
import org.springframework.test.annotation.DirtiesContext

@RunWith(SpringRunner::class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArticleControllerTest : TestBase() {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    companion object {
        private const val BASE_URI = "/v1/articles"
        private val headers = HttpHeaders()
    }

    @Test
    fun `create an article then find it and delete it`() {
        headers.contentType = MediaType.APPLICATION_JSON_UTF8
        val article = getArticleDTO()
        val entity = HttpEntity(article, headers)

        // Post
        val response = restTemplate.postForEntity(BASE_URI, entity, ArticleDTO::class.java)
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)

        val articleResponseDTO = response.body!!
        val articleID = articleResponseDTO.id

        Assertions.assertThat(articleID).isNotNull()
        Assertions.assertThat(articleResponseDTO.header).isEqualTo("some header for test")

        // Find
        val findResponse = restTemplate.getForEntity("$BASE_URI/$articleID", ArticleDTO::class.java)
        Assertions.assertThat(findResponse).isNotNull
        Assertions.assertThat(findResponse.statusCode).isEqualTo(HttpStatus.OK)

        val foundArticleDTO = findResponse.body!!
        Assertions.assertThat(foundArticleDTO.id).isEqualTo(articleID)
        Assertions.assertThat(foundArticleDTO.shortDescription).isEqualTo("some short description for test")
        Assertions.assertThat(foundArticleDTO.authors).hasSize(2)
        Assertions.assertThat(foundArticleDTO.authors[0].firstName).isEqualTo("James")
        Assertions.assertThat(foundArticleDTO.authors[0].lastName).isEqualTo("Henry")
        Assertions.assertThat(foundArticleDTO.authors[1].firstName).isEqualTo("Mary")
        Assertions.assertThat(foundArticleDTO.authors[1].lastName).isEqualTo("Writer")

        // Delete
        restTemplate.delete("$BASE_URI/$articleID")

        // Find Again
        val findAgainResponse = restTemplate.getForEntity("$BASE_URI/$articleID", String::class.java)
        Assertions.assertThat(findAgainResponse.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `create an article, edit it and delete it`() {
        headers.contentType = MediaType.APPLICATION_JSON_UTF8
        val article = getArticleDTO()
        val entity = HttpEntity(article, headers)

        // Post
        val response = restTemplate.postForEntity(BASE_URI, entity, ArticleDTO::class.java)
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)

        val articleResponseDTO = response.body!!
        val articleID = articleResponseDTO.id

        Assertions.assertThat(articleID).isNotNull()
        Assertions.assertThat(articleResponseDTO.header).isEqualTo("some header for test")
        Assertions.assertThat(articleResponseDTO.shortDescription).isEqualTo("some short description for test")
        Assertions.assertThat(articleResponseDTO.text).isEqualTo("some text for test")
        Assertions.assertThat(articleResponseDTO.publishDate).isEqualTo(LocalDate.now())
        Assertions.assertThat(articleResponseDTO.keywords).hasSize(2)
        Assertions.assertThat(articleResponseDTO.keywords[0]).isEqualTo("keyword1Test")
        Assertions.assertThat(articleResponseDTO.keywords[1]).isEqualTo("keyword2Test")
        Assertions.assertThat(articleResponseDTO.authors).hasSize(2)
        Assertions.assertThat(articleResponseDTO.authors[0].firstName).isEqualTo("James")
        Assertions.assertThat(articleResponseDTO.authors[1].firstName).isEqualTo("Mary")

        // Update/edit
        val params = HashMap<String, String>()
        params["articleId"] = articleID.toString()

        val updatedArticle = articleResponseDTO.copy()
        updatedArticle.header = "Updated header"
        updatedArticle.authorIds = listOf(1)

        val requestEntity = HttpEntity(updatedArticle, headers)
        val updateResponse = restTemplate.exchange("$BASE_URI/{articleId}",
            HttpMethod.PUT,
            requestEntity,
            ArticleDTO::class.java,
            params)

        Assertions.assertThat(updateResponse).isNotNull
        Assertions.assertThat(updateResponse.statusCode).isEqualTo(HttpStatus.OK)

        val updatedResponseDTO = updateResponse.body!!
        Assertions.assertThat(updatedResponseDTO.id).isEqualTo(articleID)
        Assertions.assertThat(updatedResponseDTO.authors).hasSize(1)
        Assertions.assertThat(updatedResponseDTO.authors[0].firstName).isEqualTo("James")
        Assertions.assertThat(updatedResponseDTO.header).isEqualTo("Updated header")
        Assertions.assertThat(articleResponseDTO.shortDescription).isEqualTo("some short description for test")
        Assertions.assertThat(articleResponseDTO.text).isEqualTo("some text for test")
        Assertions.assertThat(articleResponseDTO.publishDate).isEqualTo(LocalDate.now())
        Assertions.assertThat(articleResponseDTO.keywords).hasSize(2)
        Assertions.assertThat(articleResponseDTO.keywords[0]).isEqualTo("keyword1Test")
        Assertions.assertThat(articleResponseDTO.keywords[1]).isEqualTo("keyword2Test")

        // Delete
        restTemplate.delete("$BASE_URI/$articleID")

        // Find Again
        val findAgainResponse = restTemplate.getForEntity("$BASE_URI/$articleID", String::class.java)
        Assertions.assertThat(findAgainResponse.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `find article by author name`() {
        val response = restTemplate.exchange("$BASE_URI/authors?firstName=James&lastName=Henry",
            HttpMethod.GET, null, object : ParameterizedTypeReference<List<ArticleDTO>>() {})

        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        val articles = response.body!!
        Assertions.assertThat(articles.size).isEqualTo(2)
        Assertions.assertThat(articles[0].header).isEqualTo("some header")
        Assertions.assertThat(articles[1].header).isEqualTo("some header2")
    }

    @Test
    fun `find article by author name should not find non-existing author`() {
        val response = restTemplate.exchange("$BASE_URI/authors?firstName=James&lastName=NoMore",
            HttpMethod.GET, null, object : ParameterizedTypeReference<List<ArticleDTO>>() {})

        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(response.body!!.size).isEqualTo(0)
    }

    @Test
    fun `find article within specified dates`() {
        val uriWithDates = "$BASE_URI/dates?from=${LocalDate.now().minusDays(1)}&to=${LocalDate.now()}"

        val response = restTemplate
            .exchange(uriWithDates,
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<List<ArticleDTO>>() {})

        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        val articles = response.body!!
        Assertions.assertThat(articles.size).isEqualTo(2)
        Assertions.assertThat(articles[0].header).isEqualTo("some header")
        Assertions.assertThat(articles[1].header).isEqualTo("some header2")
    }

    @Test
    fun `find article within specified dates should not find non-existing`() {
        val uriWithDates = "$BASE_URI/dates?from=${LocalDate.now().minusDays(10)}&to=${LocalDate.now().minusDays(5)}"

        val response = restTemplate
            .exchange(uriWithDates,
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<List<ArticleDTO>>() {})

        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(response.body!!.size).isEqualTo(0)
    }

    @Test
    fun `find article with keyword`() {
        val uriWithDates = "$BASE_URI/keywords?keyword=planet"

        val response = restTemplate
            .exchange(uriWithDates,
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<List<ArticleDTO>>() {})

        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        val articlesDTO = response.body!!
        Assertions.assertThat(articlesDTO.size).isEqualTo(2)
        Assertions.assertThat(articlesDTO[0].header).isEqualTo("some header")
        Assertions.assertThat(articlesDTO[1].header).isEqualTo("some header2")
    }

    @Test
    fun `create article should fail for field validations`() {
        headers.contentType = MediaType.APPLICATION_JSON_UTF8
        val article = getArticleDTO()
        article.header = "h"
        article.shortDescription = "h"
        val entity = HttpEntity(article, headers)

        // Post
        val response = restTemplate.postForEntity(BASE_URI, entity, ArticleDTO::class.java)
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

}
