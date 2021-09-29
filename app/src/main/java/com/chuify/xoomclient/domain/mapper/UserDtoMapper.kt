package com.chuify.xoomclient.domain.mapper

import com.chuify.xoomclient.data.remote.dto.UserDto
import com.chuify.xoomclient.domain.model.User


class UserDtoMapper : DomainMapper<UserDto, User> {

    override fun mapToDomainModel(model: UserDto): User {
        return User(
            user_id = model.user_id,
            firstname = model.firstname,
            lastname = model.lastname,
            phone = model.phone,
            email = model.email,
            token = model.access_token
        )
    }

    override fun mapFromDomainModel(domainModel: User): UserDto {
        return UserDto(
            user_id = domainModel.user_id,
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