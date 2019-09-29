package com.upday.datatransferobject

import io.swagger.annotations.ApiModelProperty
import jdk.nashorn.internal.ir.annotations.Ignore
import javax.validation.constraints.*

class AuthorDTO {

    @Ignore
    @ApiModelProperty(hidden = true)
    var id: Long? = null

    @NotNull
    @NotEmpty
    @ApiModelProperty(example = "Smith", required = true)
    @Size(min = 2, max = 50)
    var lastName: String = ""

    @NotNull
    @NotEmpty
    @ApiModelProperty(example = "Oliver", required = true)
    @Size(min = 2, max = 50)
    var firstName: String = ""

    @Ignore
    @ApiModelProperty(hidden = true)
    var articles: List<ArticleDTO> = mutableListOf()
}
