package com.bokadev.word_takes.data.remote.mapper

import com.bokadev.word_takes.data.remote.dto.LoginResponseDto
import com.bokadev.word_takes.data.remote.dto.UserDto
import com.bokadev.word_takes.domain.model.LoginResponse
import com.bokadev.word_takes.domain.model.User

fun LoginResponseDto.toDomain(): LoginResponse {
    return LoginResponse(
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

fun LoginResponse.toSerializable(): LoginResponseDto {
    return LoginResponseDto(
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