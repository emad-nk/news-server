package com.upday.domainobject

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "author",
    uniqueConstraints = [UniqueConstraint(name = "unique_firstName_lastName", columnNames = ["firstName", "lastName"])])
data class AuthorDO(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @NotNull
    @Size(max = 50)
    var lastName: String?,

    @NotNull
    @Size(max = 50)
    var firstName: String?,

    @ManyToMany(mappedBy = "authors")
    var articles: MutableList<ArticleDO> = ArrayList()
)
