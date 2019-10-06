package com.upday.domainobject

import com.upday.datatransferobject.ArticleDTO
import org.modelmapper.ModelMapper
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "article")
class ArticleDO(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    @NotNull(message = "header cannot be null!")
    var header: String,

    @Column(nullable = false)
    @NotNull(message = "shortDescription cannot be null!")
    var shortDescription: String,

    @Column(nullable = false)
    @NotNull(message = "text cannot be null!")
    var text: String,

    @ElementCollection
    @CollectionTable(
        name = "keywords_table",
        joinColumns = [JoinColumn(name = "article_id")])
    var keywords: MutableList<String>,

    @Column(nullable = false)
    @NotNull(message = "publishDate cannot be null!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var publishDate: LocalDate,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "author_article",
        joinColumns = [JoinColumn(name = "author_id")],
        inverseJoinColumns = [JoinColumn(name = "article_id")])
    var authors: MutableList<AuthorDO>
)

private val mapper = ModelMapper()
fun ArticleDO.toDTO(): ArticleDTO = mapper.map(this, ArticleDTO::class.java)
fun List<ArticleDO>.toDTOList(): List<ArticleDTO> = this.map { articleDO -> articleDO.toDTO() }
