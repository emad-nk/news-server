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

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArticleControllerTest : TestBase() {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate
    private val baseUri = "/v1/articles"
    private val headers = HttpHeaders()

    @Test
    fun `create an article then find it and delete it`() {
        headers.contentType = MediaType.APPLICATION_JSON_UTF8
        val article = getArticleDTO()
        val entity = HttpEntity(article, headers)

        // Post
        val response = restTemplate.postForEntity(baseUri, entity, ArticleDTO::class.java)
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)

        val articleResponseDTO = response.body!!
        val articleID = articleResponseDTO.id

        Assertions.assertThat(articleID).isNotNull()
        Assertions.assertThat(articleResponseDTO.header).isEqualTo("some header for test")

        // Find
        val findResponse = restTemplate.getForEntity("$baseUri/id/$articleID", ArticleDTO::class.java)

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
        restTemplate.delete("$baseUri/id/$articleID")

        // Find Again
        val findAgainResponse = restTemplate.getForEntity("$baseUri/id/$articleID", String::class.java)
        Assertions.assertThat(findAgainResponse.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `find article by author name`() {
        val response = restTemplate.exchange("$baseUri/authors?firstName=James&lastName=Henry",
            HttpMethod.GET, null, object : ParameterizedTypeReference<List<ArticleDTO>>() {})

        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        val articles = response.body!!
        Assertions.assertThat(articles.size).isEqualTo(2)
        Assertions.assertThat(articles[0].header).isEqualTo("some header")
        Assertions.assertThat(articles[1].header).isEqualTo("some header2")
    }

    @Test
    fun `find article by author name should not find non-existing author`() {
        val response = restTemplate.exchange("$baseUri/authors?firstName=James&lastName=NoMore",
            HttpMethod.GET, null, object : ParameterizedTypeReference<List<ArticleDTO>>() {})

        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(response.body!!.size).isEqualTo(0)
    }

    @Test
    fun `find article within specified dates`() {
        val uriWithDates = "$baseUri/dates?from=${LocalDate.now().minusDays(1)}&to=${LocalDate.now()}"

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
        val uriWithDates = "$baseUri/dates?from=${LocalDate.now().minusDays(10)}&to=${LocalDate.now().minusDays(5)}"

        val response = restTemplate
            .exchange(uriWithDates,
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<List<ArticleDTO>>() {})

        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(response.body!!.size).isEqualTo(0)
    }
}
