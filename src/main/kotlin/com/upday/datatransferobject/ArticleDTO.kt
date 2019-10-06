package com.upday.datatransferobject

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.upday.domainobject.ArticleDO
import io.swagger.annotations.ApiModelProperty
import org.modelmapper.ModelMapper
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ArticleDTO(

    @get:JsonProperty
    @ApiModelProperty(hidden = true)
    var id: Long? = null,

    @ApiModelProperty(example = "[1, 2]", dataType = "List", required = true)
    var authorIds: List<Long>? = null,

    @get:NotNull
    @get:NotEmpty
    @ApiModelProperty(example = "This is a header", required = true)
    @get:Size(min = 5, max = 50)
    var header: String? = null,

    @get:NotNull
    @get:NotEmpty
    @ApiModelProperty(example = "This is a short description", required = true)
    @get:Size(min = 10, max = 50)
    var shortDescription: String? = null,

    @get:NotNull
    @get:NotEmpty
    @ApiModelProperty(example = "This is a long text", required = true)
    @get:Size(min = 10)
    var text: String? = null,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(example = "2019-10-05", required = true)
    @get:NotNull
    var publishDate: LocalDate? = null,

    @get:NotNull
    @ApiModelProperty(example = "[plants, green, cultivate]", dataType = "List", required = true)
    var keywords: MutableList<String> = ArrayList(),

    @ApiModelProperty(hidden = true)
    @JsonIgnoreProperties("articles")
    var authors: MutableList<AuthorDTO> = ArrayList()
)

private val mapper = ModelMapper()
fun ArticleDTO.toDO(): ArticleDO = mapper.map(this, ArticleDO::class.java)
