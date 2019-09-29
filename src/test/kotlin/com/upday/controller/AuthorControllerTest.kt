package com.upday.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.upday.TestBase
import com.upday.datatransferobject.AuthorDTO
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorControllerTest : TestBase() {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate
    private val baseUri = "/v1/authors"
    private val headers = HttpHeaders()

    @Test
    fun `create an author then find it and delete it`() {
        headers.contentType = MediaType.APPLICATION_JSON_UTF8
        val author = AuthorDTO(firstName = "Juan", lastName = "Jamon")
        val entity = HttpEntity(author, headers)

        // Post
        val response = restTemplate.postForEntity(baseUri, entity, AuthorDTO::class.java)
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)

        val responseAuthorDTO = response.body!!
        val authorID = responseAuthorDTO.id

        Assertions.assertThat(authorID).isNotNull()
        Assertions.assertThat(responseAuthorDTO.firstName).isEqualTo("Juan")

        // Find
        val findResponse = restTemplate.getForEntity("$baseUri/id/$authorID", AuthorDTO::class.java)

        Assertions.assertThat(findResponse).isNotNull
        Assertions.assertThat(findResponse.statusCode).isEqualTo(HttpStatus.OK)

        val foundAuthorDTO = findResponse.body!!
        Assertions.assertThat(foundAuthorDTO.id).isEqualTo(authorID)
        Assertions.assertThat(foundAuthorDTO.lastName).isEqualTo("Jamon")

        // Delete
        restTemplate.delete("$baseUri/id/$authorID")

        // Find Again
        val findAgainResponse = restTemplate.getForEntity("$baseUri/id/$authorID", String::class.java)
        Assertions.assertThat(findAgainResponse.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }


}
