package com.androidfactory.network.models.remote

import com.androidfactory.network.models.domain.EpisodePage
import kotlinx.serialization.Serializable

@Serializable
data class RemoteEpisodePage(
    val info: Info,
    val results: List<RemoteEpisode>
) {
    @Serializable
    data class Info(
        val count: Int,
        val pages: Int,
        val next: String?,
        val prev: String?
    )
}

fun RemoteEpisodePage.toDomainEpisodePage(): EpisodePage {
    return EpisodePage(
        info = EpisodePage.Info(
            count = info.count,
            pages = info.pages,
            next = info.next,
            prev = info.prev
        ),
        episodes = results.map { it.toDomainEpisode() }
    )
}