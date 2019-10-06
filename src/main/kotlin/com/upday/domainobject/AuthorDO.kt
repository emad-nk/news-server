package com.upday.domainobject

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "author",
    uniqueConstraints = [UniqueConstraint(name = "unique_firstName_lastName", columnNames = ["firstName", "lastName"])])
class AuthorDO(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    @NotNull(message = "lastName cannot be null!")
    var lastName: String?,

    @Column(nullable = false)
    @NotNull(message = "firstName cannot be null!")
    var firstName: String?,

    @ManyToMany(mappedBy = "authors")
    var articles: MutableList<ArticleDO> = ArrayList()
)
