package com.example.klinik.ui.view.SesiTerapis

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinik.model.SesiTerapi
import com.example.klinik.ui.PenyediaViewModel
import com.example.klinik.ui.customwidget.TopAppBar
import com.example.klinik.ui.navigation.DestinasiNavigasi
import com.example.klinik.ui.viewmodel.sesi.DetailSesiTerapiUiState
import com.example.klinik.ui.viewmodel.sesi.DetailSesiTerapiViewModel

object DestinasiDetailSesiTerapi : DestinasiNavigasi {
    override val route = "Sesi"
    override val titleRes = "Detail Sesi Terapi"
    const val ID_SESI = "id_sesi"
    val routeWithArgs = "$route/{$ID_SESI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSesiTerapiView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    detailViewModel: DetailSesiTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiDetailSesiTerapi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val idSesi = (detailViewModel.detailSesiTerapiUiState as? DetailSesiTerapiUiState.Success)?.sesiTerapi?.id_sesi
                    if (idSesi != null) onEditClick(idSesi)
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Sesi Terapi",
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding).offset(y = (-70).dp)
        ) {
            DetailSesiTerapiStatus(
                sesiTerapiUiState = detailViewModel.detailSesiTerapiUiState,
                retryAction = { detailViewModel.getSesiTerapiById() },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun DetailSesiTerapiStatus(
    sesiTerapiUiState: DetailSesiTerapiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (sesiTerapiUiState) {
        is DetailSesiTerapiUiState.Success -> {
            DetailSesiTerapiCard(
                sesiTerapi = sesiTerapiUiState.sesiTerapi,
                modifier = modifier.padding(16.dp)
            )
        }

        is DetailSesiTerapiUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailSesiTerapiUiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Terjadi kesalahan.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = retryAction) {
                        Text(text = "Coba Lagi")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailSesiTerapiCard(
    sesiTerapi: SesiTerapi,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium,
        //     shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFECD5E7)
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ComponentDetailSesiTerapi(judul = "ID Sesi", isinya = sesiTerapi.id_sesi)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailSesiTerapi(judul = "ID Pasien", isinya = sesiTerapi.id_pasien)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailSesiTerapi(judul = "ID Terapis", isinya = sesiTerapi.id_terapis)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailSesiTerapi(judul = "ID Jenis Terapi", isinya = sesiTerapi.id_jenis_terapi)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailSesiTerapi(judul = "Tanggal Sesi", isinya = sesiTerapi.tanggal_sesi)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailSesiTerapi(judul = "Catatan Sesi", isinya = sesiTerapi.catatan_sesi)
        }
    }
}

@Composable
fun ComponentDetailSesiTerapi(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$judul:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6A6A6A), // Gray untuk teks judul, bisa disesuaikan
            modifier = Modifier.weight(1f) // Memberikan space di sebelah kiri
        )
        Text(
            text = isinya,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF333333), // Teks konten lebih gelap untuk kontras
            modifier = Modifier.weight(2f), // Memberikan space di sebelah kanan
            maxLines = 2, // Jika teks terlalu panjang, batasi tampilannya
            overflow = TextOverflow.Ellipsis // Jika teks panjang, beri tanda elipsis
        )
    }
}

