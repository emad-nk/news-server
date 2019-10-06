package com.upday.controller

import com.upday.datatransferobject.AuthorDTO
import com.upday.datatransferobject.toDO
import com.upday.domainobject.toDTO
import com.upday.domainobject.toDTOList
import com.upday.service.AuthorService
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * All operations with an author will be routed by this controller.
 */
@RestController
@RequestMapping("v1/authors")
class AuthorController(private val authorService: AuthorService) {

    @ApiOperation(value = "Add a new author")
    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createAuthor(@RequestBody @Valid authorDTO: AuthorDTO): AuthorDTO {
        val authorDO = authorDTO.toDO()
        return authorService.create(authorDO).toDTO()
    }

    @ApiOperation(value = "Get author with id")
    @GetMapping("/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    fun getAuthor(@PathVariable authorId: Long): AuthorDTO {
        return authorService.getAuthorById(authorId).toDTO()
    }

    @ApiOperation(value = "Get all the authors")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAuthors(): List<AuthorDTO> {
        return authorService.getAllAuthors().toDTOList()
    }

    @ApiOperation(value = "Update an author")
    @PutMapping("/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateAuthor(@PathVariable authorId: Long, @RequestBody @Valid authorDTO: AuthorDTO): AuthorDTO {
        return authorService.updateAuthor(authorId, authorDTO.toDO()).toDTO()
    }

    @ApiOperation(value = "Delete an author")
    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAuthor(@PathVariable authorId: Long) {
        authorService.deleteAuthor(authorId)
    }
}
