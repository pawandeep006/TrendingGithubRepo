package com.pawan.trendinggithubrepo.source

import com.pawan.trendinggithubrepo.source.network.ApiService
import com.pawan.trendinggithubrepo.source.network.model.Item
import javax.inject.Inject

class NetworkRepo @Inject internal constructor(private val apiService: ApiService) {

    @Throws(Exception::class)
    suspend fun fetchRepoWithQuery(query: String, sort: String): List<Item> {
        val response = apiService.fetchRepo(
            query,
            sort
        )
        if (!response.isSuccessful)
            throw Exception("Something went wrong. Please try again later.")

        val responseBody = response.body()

        responseBody?.items?.let {
            return it
        }

        throw Exception("Internal server error. Please try again later.")
    }
}

