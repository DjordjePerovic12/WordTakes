package com.bokadev.word_takes.data.remote.mapper

import com.bokadev.word_takes.data.remote.dto.*
import com.bokadev.word_takes.domain.model.*
import kotlinx.datetime.Instant
import org.koin.core.qualifier.named


fun ReactionsDto.toDomain(): Reactions =
    Reactions(good = good, amazing = amazing, bad = bad, awful = awful, skipped = skipped)

fun PaginationMetaDto.toDomain(): PaginationMeta =
    PaginationMeta(
        currentPage = currentPage,
        lastPage = lastPage,
        perPage = perPage,
        total = total
    )

fun RatingResponseDto.toDomain(): Rating =
    Rating(
        user = user.toDomain(),
        reaction = reaction,
        createdAt = kotlin.time.Instant.parse(createdAt) // backend ISO string
    )

fun List<UserDto>.toUserList(): List<User> {
    return this.map {
        User(
            id = it.id,
            name = it.name,
            email = it.email
        )
    }
}

fun PaginatedRatingsResponseDto.toDomain(): PaginatedRatings =
    PaginatedRatings(
        wordId = wordId,
        reactions = totals.toDomain(),
        items = data.map { it.toDomain() },
        meta = meta.toDomain()
    )
