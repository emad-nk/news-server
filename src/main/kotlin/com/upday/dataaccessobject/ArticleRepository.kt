package com.upday.dataaccessobject

import com.upday.domainobject.ArticleDO
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Database Access Object for article table.
 */
@Repository
interface ArticleRepository: CrudRepository<ArticleDO, Long>, JpaSpecificationExecutor<ArticleDO>
