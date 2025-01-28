package com.example.klinik.service_api
import com.example.klinik.model.SesiTerapi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface SesiTerapiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("sesiterapi/bacasesiterapi.php")
    suspend fun getSesi(): List<SesiTerapi>

    @GET("sesiterapi/baca1sesiterapi.php/{id_sesi}")
    suspend fun getSesiById(@Query("id_sesi") id_sesi: String): SesiTerapi

    @POST("sesiterapi/insertsesiterapi.php")
    suspend fun insertSesi(@Body sesiTerapi: SesiTerapi)

    @PUT("sesiterapi/editsesiterapi.php/{id_sesi}")
    suspend fun updateSesi(@Query("id_sesi") id_sesi: String, @Body sesiTerapi: SesiTerapi)

    @DELETE("sesiterapi/deletesesiterapi.php/{id_sesi}")
    suspend fun deleteSesi(@Query("id_sesi") id_sesi: String): Response<Void>
}