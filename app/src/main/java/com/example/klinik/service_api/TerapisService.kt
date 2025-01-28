package com.example.klinik.service_api

import com.example.klinik.model.Terapis
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TerapisService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("terapis/bacaterapis.php")
    suspend fun getTerapis(): List<Terapis>

    @GET("terapis/baca1terapis.php/{id_terapis}")
    suspend fun getTerapisById(@Query("id_terapis") id_terapis: String): Terapis

    @POST("terapis/insertterapis.php")
    suspend fun insertTerapis(@Body terapis: Terapis)

    @PUT("terapis/editterapis.php/{id_terapis}")
    suspend fun updateTerapis(@Query("id_terapis") id_terapis: String, @Body terapis: Terapis)

    @DELETE("terapis/deleteterapis.php/{id_terapis}")
    suspend fun deleteTerapis(@Query("id_terapis") id_terapis: String):Response<Void>

}