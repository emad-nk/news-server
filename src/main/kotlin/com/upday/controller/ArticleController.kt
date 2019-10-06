package com.upday.controller

import com.upday.datatransferobject.ArticleDTO
import com.upday.service.article.ArticleService
import com.upday.util.MapperUtil
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
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
        val articleDO = MapperUtil.makeArticleDO(articleDTO)
        return MapperUtil.makeArticleDTO(articleService.create(articleDO, articleDTO.authorIds!!))
    }

    @ApiOperation(value = "Add a new article")
    @PutMapping("/update/id/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateArticle(@PathVariable articleId: Long, @RequestBody @Valid articleDTO: ArticleDTO): ArticleDTO {
        val articleDO = MapperUtil.makeArticleDO(articleDTO)
        return MapperUtil.makeArticleDTO(articleService.updateArticle(articleId, articleDO, articleDTO.authorIds!!))
    }

    @ApiOperation(value = "Get article with id")
    @GetMapping("/id/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    fun getArticle(@PathVariable articleId: Long): ArticleDTO {
        return MapperUtil.makeArticleDTO(articleService.getArticleById(articleId))
    }

    @ApiOperation(value = "Get all the articles written by specific author")
    @GetMapping("/authors")
    @ResponseStatus(HttpStatus.OK)
    fun getArticlesByAuthorName(@RequestParam firstName: String, @RequestParam lastName: String): List<ArticleDTO> {
        return MapperUtil.makeArticleDTOList(articleService.getArticlesFromAuthor(firstName, lastName))
    }

    @ApiOperation(value = "Get all the articles within specified period (yyyy-MM-dd)")
    @GetMapping("/dates")
    @ResponseStatus(HttpStatus.OK)
    fun getArticlesWithinPeriod(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate)
        : List<ArticleDTO> {
        return MapperUtil.makeArticleDTOList(articleService.getArticlesWithinPeriod(from, to))
    }

    @ApiOperation(value = "Get all the articles with specified keyword")
    @GetMapping("/keywords/{keyword}")
    @ResponseStatus(HttpStatus.OK)
    fun getArticlesWithKeyword(@PathVariable keyword: String): List<ArticleDTO> {
        return MapperUtil.makeArticleDTOList(articleService.getArticlesWithKeyword(keyword))
    }

    @ApiOperation(value = "Delete article with id")
    @DeleteMapping("/id/{articleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteArticle(@PathVariable articleId: Long) {
        articleService.deleteArticle(articleId)
    }
}
