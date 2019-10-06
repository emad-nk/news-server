package com.upday.controller

import com.upday.datatransferobject.AuthorDTO
import com.upday.service.author.AuthorService
import com.upday.util.MapperUtil
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
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
        val authorDO = MapperUtil.makeAuthorDO(authorDTO)
        return MapperUtil.makeAuthorDTO(authorService.create(authorDO))
    }

    @ApiOperation(value = "Get author with id")
    @GetMapping("/id/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    fun getAuthor(@PathVariable authorId: Long): AuthorDTO {
        return MapperUtil.makeAuthorDTO(authorService.getAuthorById(authorId))
    }

    @ApiOperation(value = "Get all the authors")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAuthors(): List<AuthorDTO> {
        return MapperUtil.makeAuthorDTOList(authorService.getAllAuthors())
    }

    @ApiOperation(value = "Update an author")
    @PutMapping("/id/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateAuthor(@PathVariable authorId: Long, @RequestBody @Valid authorDTO: AuthorDTO): AuthorDTO {
        val authorDO = MapperUtil.makeAuthorDO(authorDTO)
        return MapperUtil.makeAuthorDTO(authorService.updateAuthor(authorId, authorDO))
    }

    @ApiOperation(value = "Delete an author")
    @DeleteMapping("/id/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAuthor(@PathVariable authorId: Long) {
        authorService.deleteAuthor(authorId)
    }
}
