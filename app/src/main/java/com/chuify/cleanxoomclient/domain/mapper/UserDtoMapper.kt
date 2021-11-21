package com.chuify.cleanxoomclient.domain.mapper

import com.chuify.cleanxoomclient.data.remote.dto.UserDto
import com.chuify.cleanxoomclient.domain.model.User


class UserDtoMapper : DomainMapper<UserDto, User> {

    override fun mapToDomainModel(model: UserDto): User {
        return User(
            userId = model.user_id!!,
            firstname = model.firstname!!,
            lastname = model.lastname!!,
            phone = model.phone!!,
            email = model.email!!,
            token = model.access_token!! ,
            points = String()
        )
    }

    fun mapFromDomainModel(domainModel: User): UserDto {
        return UserDto(
            user_id = domainModel.userId,
            firstname = domainModel.firstname,
            lastname = domainModel.lastname,
            phone = domainModel.phone,
            email = domainModel.email,
            access_token = domainModel.token,
            msg = "",
            status = ""
        )
    }


}