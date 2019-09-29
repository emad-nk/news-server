package com.upday.controller

import com.upday.service.article.ArticleService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
/**
 * All operations with an article will be routed by this controller.
 */
@RestController
@RequestMapping("v1/articles")
class ArticleController(private val articleService: ArticleService) {

    companion object {
        private val log = LoggerFactory.getLogger(ArticleController::class.java)!!
    }
}
