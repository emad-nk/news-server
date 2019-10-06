package com.upday.domainobject

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
    var header: String,

    @NotNull
    @Size(max = 100)
    var shortDescription: String,

    @NotNull
    var text: String,

    @ElementCollection
    @CollectionTable(
        name="keywords_table",
        joinColumns=[JoinColumn(name="article_id")])
    var keywords: MutableList<String> = ArrayList(),

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var publishDate: LocalDate,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "author_article",
        joinColumns = [JoinColumn(name = "author_id")],
        inverseJoinColumns = [JoinColumn(name = "article_id")])
    var authors: MutableList<AuthorDO> = ArrayList()
)
