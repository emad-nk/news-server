package com.upday.datatransferobject

import io.swagger.annotations.ApiModelProperty
import jdk.nashorn.internal.ir.annotations.Ignore
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class ArticleDTO {

    @Ignore
    @ApiModelProperty(hidden = true)
    var id: Long? = null

    @NotNull
    @NotEmpty
    @ApiModelProperty(example = "This is a header", required = true)
    @Size(min = 5, max = 50)
    var header: String = ""

    @NotNull
    @NotEmpty
    @ApiModelProperty(example = "This is a short description", required = true)
    @Size(min = 10, max = 50)
    var shortDescription: String = ""

    @NotNull
    @NotEmpty
    @ApiModelProperty(example = "This is a long text", required = true)
    @Size(min = 10)
    var text: String = ""

    @NotNull
    @NotEmpty
    @ApiModelProperty(example = "[keyword1, keyword2, keyword3]", dataType = "List", required = true)
    var keywords: List<String> = mutableListOf()

    @NotNull
    @NotEmpty
    @ApiModelProperty(example = "[keyword1, keyword2, keyword3]", dataType = "List", required = true)
    var authors: List<AuthorDTO> = mutableListOf()

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(example = "2019-10-05", required = true)
    @NotNull
    var publishDate: LocalDate? = null
}
