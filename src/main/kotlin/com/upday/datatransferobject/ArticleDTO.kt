package com.upday.datatransferobject

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
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

    @NotNull
    @NotEmpty
    @ApiModelProperty(example = "This is a header", required = true)
    @Size(min = 5, max = 50)
    var header: String? = null,

    @NotNull
    @NotEmpty
    @ApiModelProperty(example = "This is a short description", required = true)
    @Size(min = 10, max = 50)
    var shortDescription: String? = null,

    @NotNull
    @NotEmpty
    @ApiModelProperty(example = "This is a long text", required = true)
    @Size(min = 10)
    var text: String? = null,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(example = "2019-10-05", required = true)
    @NotNull
    var publishDate: LocalDate? = null,

    @NotNull
    @ApiModelProperty(example = "[plants, green, cultivate]", dataType = "List", required = true)
    var keywords: ArrayList<String> = ArrayList(),

    @ApiModelProperty(hidden = true)
    @JsonIgnoreProperties("articles")
    var authors: MutableList<AuthorDTO> = ArrayList()
)
