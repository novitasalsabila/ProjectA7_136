package com.example.klinik.service_api

import com.example.klinik.model.JenisTerapi
import com.example.klinik.model.Pasien
import retrofit2.Response
import retrofit2.http.*

interface JenisTerapiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("jenisterapi/bacajenisterapi.php")
    suspend fun getAllJenisTerapi(): List<JenisTerapi>

    @GET("jenisterapi/baca1jenisterapi.php/{id_jenis_terapi}")
    suspend fun getJenisTerapiById(@Query("id_jenis_terapi") id_jenis_terapi: String): JenisTerapi

    @POST("jenisterapi/insertjenisterapi.php")
    suspend fun insertJenisTerapi(@Body id_jenis_terapi: JenisTerapi)

    @PUT("jenisterapi/editjenisterapi.php/{id_jenis_terapi}")
    suspend fun updateJenisTerapi(@Query("id_jenis_terapi") id_jenis_terapi: String, @Body jenisTerapi: JenisTerapi)

    @DELETE("jenisterapi/deletejenisterapi.php/{id_jenis_terapi}")
    suspend fun deleteJenisTerapi(@Query("id_jenis_terapi") id_jenis_terapi: String):Response<Void>

}
