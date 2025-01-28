package com.example.klinik.ui.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiMain: DestinasiNavigasi{
    override val route  = "homeapp"
    override val titleRes = "Home"
}
object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Daftar Pasien"
}
object DestinasiDaftarTerapis : DestinasiNavigasi {
    override val route = "Terapis"
    override val titleRes = "Daftar Terapis"
}

object DestinasiDaftarJenisTerapi : DestinasiNavigasi {
    override val route = "Jenis"
    override val titleRes = "Daftar Jenis Terapi"
}

object DestinasiSesiTerapi : DestinasiNavigasi {
    override val route = "Sesi"
    override val titleRes = "Sesi Terapi"
}
