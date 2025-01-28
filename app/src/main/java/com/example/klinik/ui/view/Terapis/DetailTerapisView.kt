package com.example.klinik.ui.view.Terapis

import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinik.model.Terapis
import com.example.klinik.ui.viewmodel.terapisVM.DetailTerapisUiState
import com.example.klinik.ui.viewmodel.terapisVM.DetailTerapisViewModel
import com.example.klinik.ui.PenyediaViewModel
import com.example.klinik.ui.customwidget.TopAppBar
import com.example.klinik.ui.navigation.DestinasiNavigasi

object DestinasiDetailTerapis : DestinasiNavigasi {
    override val route = "terapis_detail"
    override val titleRes = "Detail Terapis"
    const val ID_TERAPIS = "id_terapis"
    val routeWithArgs = "$route/{$ID_TERAPIS}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTerapisView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    detailViewModel: DetailTerapisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiDetailTerapis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val id_terapis = (detailViewModel.detailTerapisUiState as? DetailTerapisUiState.Success)?.terapis?.id_terapis
                    if (id_terapis != null) onEditClick(id_terapis)
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Terapis",
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding).offset(y = (-70).dp)
        ) {
            DetailTerapisStatus(
                terapisUiState = detailViewModel.detailTerapisUiState,
                retryAction = { detailViewModel.getTerapisById() },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun DetailTerapisStatus(
    terapisUiState: DetailTerapisUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (terapisUiState) {
        is DetailTerapisUiState.Success -> {
            DetailTerapisCard(
                terapis = terapisUiState.terapis,
                modifier = modifier.padding(16.dp)
            )
        }

        is DetailTerapisUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailTerapisUiState.Error -> {
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
fun DetailTerapisCard(
    terapis: Terapis,
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ComponentDetailTerapis(judul = "ID Terapis", isinya = terapis.id_terapis)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailTerapis(judul = "Nama", isinya = terapis.nama_terapis)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailTerapis(judul = "Spesialisasi", isinya = terapis.spesialisasi)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailTerapis(judul = "Nomor Izin Praktik", isinya = terapis.nomor_izin_praktik)
        }
    }
}

@Composable
fun ComponentDetailTerapis(
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
