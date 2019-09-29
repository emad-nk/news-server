package com.upday.util

import com.upday.datatransferobject.AuthorDTO
import com.upday.domainobject.AuthorDO
import org.modelmapper.ModelMapper

class MapperUtil {

    companion object {
        private val mapper = ModelMapper()
        fun toDO(dto: AuthorDTO): AuthorDO {
            return mapper.map(dto, AuthorDO::class.java)
        }

        fun toDTO(dataObject: AuthorDO): AuthorDTO {
            return mapper.map(dataObject, AuthorDTO::class.java)
        }
    }
}
