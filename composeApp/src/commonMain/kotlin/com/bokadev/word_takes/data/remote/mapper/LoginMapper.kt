package com.bokadev.word_takes.data.remote.mapper

import com.bokadev.word_takes.data.remote.dto.AuthInfoResponseDto
import com.bokadev.word_takes.data.remote.dto.UserDto
import com.bokadev.word_takes.domain.model.AuthInfo
import com.bokadev.word_takes.domain.model.User

fun AuthInfoResponseDto.toDomain(): AuthInfo {
    return AuthInfo(
        user = user.toDomain(),
        token = token
    )
}

fun UserDto.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email
    )
}

fun AuthInfo.toSerializable(): AuthInfoResponseDto {
    return AuthInfoResponseDto(
        user = user.toSerializable(),
        token = token
    )
}

fun User.toSerializable(): UserDto {
    return UserDto(
        id = id,
        name = name,
        email = email
    )
}