package com.androidfactory.simplerick.repositories

import com.androidfactory.network.ApiOperation
import com.androidfactory.network.KtorClient
import com.androidfactory.network.models.domain.Episode
import javax.inject.Inject

class EpisodesRepository @Inject constructor(private val ktorClient: KtorClient) {

    suspend fun fetchAllEpisodes(): ApiOperation<List<Episode>> = ktorClient.getAllEpisodes()
}