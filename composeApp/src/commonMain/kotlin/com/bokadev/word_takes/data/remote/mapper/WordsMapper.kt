import com.bokadev.word_takes.data.remote.dto.ReactionsDto
import com.bokadev.word_takes.data.remote.dto.WordDto
import com.bokadev.word_takes.data.remote.dto.WordsPageResponseDto
import com.bokadev.word_takes.domain.model.PaginatedWords
import com.bokadev.word_takes.domain.model.PaginationLinks
import com.bokadev.word_takes.domain.model.PaginationMeta
import com.bokadev.word_takes.domain.model.Reactions
import com.bokadev.word_takes.domain.model.WordItem

fun WordsPageResponseDto.toDomain(): PaginatedWords {
    return PaginatedWords(
        items = data.map { it.toDomain() },
        meta = PaginationMeta(
            currentPage = currentPage,
            lastPage = lastPage,
            perPage = perPage,
            total = total
        ),
        links = PaginationLinks(
            first = firstPageUrl,
            last = lastPageUrl,
            prev = prevPageUrl,
            next = nextPageUrl
        )
    )
}

fun WordDto.toDomain(): WordItem =
    WordItem(
        name = name,
        createdAtIso = createdAt,
        word = word,
        reactions = reactions.toDomain()
    )

fun ReactionsDto.toDomain(): Reactions =
    Reactions(good = good, amazing = amazing, bad = bad, awful = awful)
