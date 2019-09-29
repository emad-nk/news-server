package com.upday.domainobject

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "article")
data class ArticleDO(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @NotNull
    @Size(max = 50)
    @get:JsonProperty
    var header: String = "",

    @NotNull
    @Size(max = 100)
    @get:JsonProperty
    var shortDescription: String = "",

    @NotNull
    @get:JsonProperty
    var text: String = "",

    @NotNull
    @get:JsonProperty
    var keywords: ArrayList<String>,

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @get:JsonProperty
    var publishDate: LocalDate? = null,

    @ManyToMany(mappedBy = "articles")
    var authors: List<AuthorDO> = mutableListOf()
)
