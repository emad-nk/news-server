package com.upday.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import springfox.documentation.annotations.ApiIgnore

/**
 * Redirects localhost:8080 to swagger
 */
@Controller
@ApiIgnore
class HomeController {

    @RequestMapping("/")
    fun home() = "redirect:swagger-ui.html"
}
