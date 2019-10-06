package com.upday.datatransferobject

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.*

data class AuthorDTO(

    @get:JsonProperty
    @ApiModelProperty(hidden = true)
    var id: Long? = null,

    @get:NotNull
    @get:NotEmpty
    @ApiModelProperty(example = "Smith", required = true)
    @get:Size(min = 2, max = 50)
    var lastName: String? = null,

    @get:NotNull
    @get:NotEmpty
    @ApiModelProperty(example = "Oliver", required = true)
    @get:Size(min = 2, max = 50)
    var firstName: String? = null,

    @ApiModelProperty(hidden = true)
    @JsonIgnoreProperties("authors")
    var articles: MutableList<ArticleDTO> = ArrayList()
)
