package com.example.klinik.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pasien(
    @SerialName("id_pasien") val id_pasien: String,
    @SerialName("nama_pasien") val nama_pasien: String,
    @SerialName("alamat") val alamat: String,
    @SerialName("nomor_telepon") val nomor_telepon: String,
    @SerialName("tanggal_lahir") val tanggal_lahir: String,
    @SerialName("riwayat_medikal") val riwayat_medikal: String
)
