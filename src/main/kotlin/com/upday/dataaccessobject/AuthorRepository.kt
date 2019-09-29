package com.upday.dataaccessobject

import com.upday.domainobject.AuthorDO
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Database Access Object for author table.
 */
@Repository
interface AuthorRepository: CrudRepository<AuthorDO, Long>
