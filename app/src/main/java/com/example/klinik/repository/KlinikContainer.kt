package com.example.klinik.repository


import com.example.klinik.service_api.JenisTerapiService
import com.example.klinik.service_api.PasienService
import com.example.klinik.service_api.SesiTerapiService
import com.example.klinik.service_api.TerapisService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pasienRepository: PasienRepository
    val terapisRepository : TerapisRepository
    val jenisTerapiRepository : JenisTerapiRepository
    val sesiTerapiRepository:SesiTerapiRepository
}

class KlinikContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:8090/umyTI/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()
    //PASIEN
    private val pasienService: PasienService by lazy { retrofit.create(PasienService::class.java) }
    override val pasienRepository: PasienRepository by lazy { NetworkPasienRepository(pasienService) }
    //TERAPIS
    private val terapisService: TerapisService by lazy { retrofit.create(TerapisService::class.java) }
    override val terapisRepository: TerapisRepository by lazy { NetworkTerapisRepository(terapisService) }
    //SESI TERAPI
    private  val sesiTerapiService: SesiTerapiService by lazy { retrofit.create(SesiTerapiService::class.java) }
    override val sesiTerapiRepository: SesiTerapiRepository by lazy { NetworkSesiTerapiRepository(sesiTerapiService) }
    //JENIS TERAPI
    private val jenisTerapiService: JenisTerapiService by lazy { retrofit.create(JenisTerapiService::class.java) }
    override val jenisTerapiRepository: JenisTerapiRepository by lazy { NetworkJenisTerapiRepository(jenisTerapiService) }
}