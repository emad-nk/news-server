package com.upday.controller

import com.upday.datatransferobject.ArticleDTO
import com.upday.datatransferobject.toDO
import com.upday.domainobject.toDTO
import com.upday.domainobject.toDTOList
import com.upday.service.ArticleService
import io.swagger.annotations.ApiOperation
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.validation.Valid

/**
 * All operations with an article will be routed by this controller.
 */
@RestController
@RequestMapping("v1/articles")
class ArticleController(private val articleService: ArticleService) {

    @ApiOperation(value = "Add a new article")
    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createArticle(@RequestBody @Valid articleDTO: ArticleDTO): ArticleDTO {
        return articleService.create(articleDTO.toDO(), articleDTO.authorIds!!).toDTO()
    }

    @ApiOperation(value = "Add a new article")
    @PutMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateArticle(@PathVariable articleId: Long, @RequestBody @Valid articleDTO: ArticleDTO): ArticleDTO {
        return articleService.updateArticle(articleId, articleDTO.toDO(), articleDTO.authorIds!!).toDTO()
    }

    @ApiOperation(value = "Get article with id")
    @GetMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    fun getArticle(@PathVariable articleId: Long): ArticleDTO {
        return articleService.getArticleById(articleId).toDTO()
    }

    @ApiOperation(value = "Get all the articles written by specific author")
    @GetMapping("/authors")
    @ResponseStatus(HttpStatus.OK)
    fun getArticlesByAuthorName(@RequestParam firstName: String, @RequestParam lastName: String): List<ArticleDTO> {
        return articleService.getArticlesFromAuthor(firstName, lastName).toDTOList()
    }

    @ApiOperation(value = "Get all the articles within specified period (yyyy-MM-dd)")
    @GetMapping("/dates")
    @ResponseStatus(HttpStatus.OK)
    fun getArticlesWithinPeriod(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate)
        : List<ArticleDTO> {
        return articleService.getArticlesWithinPeriod(from, to).toDTOList()
    }

    @ApiOperation(value = "Get all the articles with specified keyword")
    @GetMapping("/keywords")
    @ResponseStatus(HttpStatus.OK)
    fun getArticlesWithKeyword(@RequestParam keyword: String): List<ArticleDTO> {
        return articleService.getArticlesWithKeyword(keyword).toDTOList()
    }

    @ApiOperation(value = "Delete article with id")
    @DeleteMapping("/{articleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteArticle(@PathVariable articleId: Long) {
        articleService.deleteArticle(articleId)
    }
}
