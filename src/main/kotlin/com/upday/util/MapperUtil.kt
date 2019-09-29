package com.upday.util

import com.upday.datatransferobject.ArticleDTO
import com.upday.datatransferobject.AuthorDTO
import com.upday.domainobject.ArticleDO
import com.upday.domainobject.AuthorDO
import org.modelmapper.ModelMapper

class MapperUtil {

    companion object {
        private val mapper = ModelMapper()
        fun makeAuthorDO(authorDTO: AuthorDTO): AuthorDO {
            return mapper.map(authorDTO, AuthorDO::class.java)
        }

        fun makeAuthorDTO(authorDO: AuthorDO): AuthorDTO {
            return mapper.map(authorDO, AuthorDTO::class.java)
        }

        fun makeArticleDO(articleDTO: ArticleDTO): ArticleDO {
            return mapper.map(articleDTO, ArticleDO::class.java)
        }

        fun makeArticleDTO(dataObject: ArticleDO): ArticleDTO {
            return mapper.map(dataObject, ArticleDTO::class.java)
        }

        fun makeAuthorDTOList(authors :List<AuthorDO>): List<AuthorDTO> {
            return authors.map { authorDO -> makeAuthorDTO(authorDO)  }.toList()
        }
    }
}
