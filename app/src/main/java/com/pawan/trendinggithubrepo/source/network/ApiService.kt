package com.pawan.trendinggithubrepo.source.network

import com.pawan.trendinggithubrepo.source.network.model.RepoResponseWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/search/repositories")
    suspend fun fetchRepo(
        @Query("q") keyword: String,
        @Query("sort") sort: String
    ): Response<RepoResponseWrapper>
}