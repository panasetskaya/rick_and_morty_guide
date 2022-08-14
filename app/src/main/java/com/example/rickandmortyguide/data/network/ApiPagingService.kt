package com.example.rickandmortyguide.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiPagingService {


    @GET("character")
    suspend fun getPagingCharactersExample(
        @Query("page") page: Int
    ): CharactersListContainer

    @GET("character")
    suspend fun getSearchedCharactersExample(
        @Query("page") page: Int,
        @Query("name") name: String
    ): CharactersListContainer

}