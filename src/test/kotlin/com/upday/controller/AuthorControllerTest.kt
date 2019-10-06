package com.upday.controller

import com.upday.TestBase
import com.upday.datatransferobject.AuthorDTO
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorControllerTest : TestBase() {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    companion object {
        private const val BASE_URI = "/v1/authors"
        private val headers = HttpHeaders()
    }

    @Test
    fun `create an author then find it and delete it`() {
        headers.contentType = MediaType.APPLICATION_JSON_UTF8
        val author = AuthorDTO(firstName = "Juan", lastName = "Jamon")
        val entity = HttpEntity(author, headers)

        // Post
        val response = restTemplate.postForEntity(BASE_URI, entity, AuthorDTO::class.java)
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)

        val responseAuthorDTO = response.body!!
        val authorID = responseAuthorDTO.id

        Assertions.assertThat(authorID).isNotNull()
        Assertions.assertThat(responseAuthorDTO.firstName).isEqualTo("Juan")

        // Find
        val findResponse = restTemplate.getForEntity("$BASE_URI/$authorID", AuthorDTO::class.java)

        Assertions.assertThat(findResponse).isNotNull
        Assertions.assertThat(findResponse.statusCode).isEqualTo(HttpStatus.OK)

        val foundAuthorDTO = findResponse.body!!
        Assertions.assertThat(foundAuthorDTO.id).isEqualTo(authorID)
        Assertions.assertThat(foundAuthorDTO.lastName).isEqualTo("Jamon")

        // Delete
        restTemplate.delete("$BASE_URI/$authorID")

        // Find Again
        val findAgainResponse = restTemplate.getForEntity("$BASE_URI/$authorID", String::class.java)
        Assertions.assertThat(findAgainResponse.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `create an author, update and delete`() {
        headers.contentType = MediaType.APPLICATION_JSON_UTF8
        val author = AuthorDTO(firstName = "Juan", lastName = "Jamon")
        val entity = HttpEntity(author, headers)

        // Post
        val response = restTemplate.postForEntity(BASE_URI, entity, AuthorDTO::class.java)
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)

        val responseAuthorDTO = response.body!!
        val authorID = responseAuthorDTO.id

        Assertions.assertThat(authorID).isNotNull()
        Assertions.assertThat(responseAuthorDTO.firstName).isEqualTo("Juan")
        Assertions.assertThat(responseAuthorDTO.lastName).isEqualTo("Jamon")

        // Update/edit
        val params = HashMap<String, String>()
        params["authorId"] = authorID.toString()

        val updatedAuthor = responseAuthorDTO.copy()
        updatedAuthor.firstName = "Alex"
        updatedAuthor.lastName = "Bach"

        val requestEntity = HttpEntity(updatedAuthor, headers)
        val updateResponse = restTemplate.exchange("$BASE_URI/{authorId}",
            HttpMethod.PUT,
            requestEntity,
            AuthorDTO::class.java,
            params)


        Assertions.assertThat(updateResponse).isNotNull
        Assertions.assertThat(updateResponse.statusCode).isEqualTo(HttpStatus.OK)

        val foundAuthorDTO = updateResponse.body!!
        Assertions.assertThat(foundAuthorDTO.id).isEqualTo(authorID)
        Assertions.assertThat(foundAuthorDTO.firstName).isEqualTo("Alex")
        Assertions.assertThat(foundAuthorDTO.lastName).isEqualTo("Bach")

        // Delete
        restTemplate.delete("$BASE_URI/$authorID")

        // Find Again
        val findAgainResponse = restTemplate.getForEntity("$BASE_URI/$authorID", String::class.java)
        Assertions.assertThat(findAgainResponse.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `create author should fail for field validations`() {
        headers.contentType = MediaType.APPLICATION_JSON_UTF8
        val author = AuthorDTO(firstName = "J", lastName = "J")
        val entity = HttpEntity(author, headers)

        // Post
        val response = restTemplate.postForEntity(BASE_URI, entity, AuthorDTO::class.java)
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

}
