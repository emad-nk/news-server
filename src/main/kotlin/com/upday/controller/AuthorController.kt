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

    companion object {
        private val log = LoggerFactory.getLogger(AuthorController::class.java)!!
    }
}
