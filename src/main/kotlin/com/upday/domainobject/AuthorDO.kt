package com.upday.domainobject

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "author", uniqueConstraints = [UniqueConstraint(name = "uc_name", columnNames = ["firstName", "lastName"])])
data class AuthorDO(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @NotNull
    @Size(max = 50)
    @get:JsonProperty
    var lastName: String = "",

    @NotNull
    @Size(max = 50)
    @get:JsonProperty
    var firstName: String = "",

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "author_article",
        joinColumns = [JoinColumn(name = "article_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")])
    var articles: List<ArticleDO> = mutableListOf()
)
