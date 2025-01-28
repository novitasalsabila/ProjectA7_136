package com.example.klinik.ui.view.Pasien

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.klinik.ui.PenyediaViewModel
import com.example.klinik.ui.navigation.DestinasiNavigasi
import com.example.klinik.ui.viewmodel.pasien.InsertPasienUiEvent
import com.example.klinik.ui.viewmodel.pasien.InsertPasienUiState
import com.example.klinik.ui.viewmodel.pasien.InsertPasienViewModel
import kotlinx.coroutines.launch

object DestinasiPasienEntry : DestinasiNavigasi {
    override val route = "pasien_entry"
    override val titleRes = "Entry Pasien"
    const val ID_PASIEN = "id_pasien"
    val routeWithArgs = "${DestinasiDetailPasien.route}/{$ID_PASIEN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPasienScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: InsertPasienViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Daftar Pasien",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        EntryPasienBody(
            insertUiState = viewModel.uiState,
            onPasienValueChange = viewModel::updateInsertPasienState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPasien()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )

        println(viewModel::updateInsertPasienState)
    }
}

@Composable
fun EntryPasienBody(
    insertUiState: InsertPasienUiState,
    onPasienValueChange: (InsertPasienUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize().padding(20.dp)
    ) {
        FormInputPasien(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onPasienValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        println(insertUiState.insertUiEvent)
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPasien(
    insertUiEvent: InsertPasienUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPasienUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.id_pasien,
            onValueChange = { onValueChange(insertUiEvent.copy(id_pasien = it)) },
            label = { Text("ID Pasien") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.nama_pasien,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_pasien = it)) },
            label = { Text("Nama Pasien") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        println(insertUiEvent.nama_pasien)
        OutlinedTextField(
            value = insertUiEvent.alamat,
            onValueChange = { onValueChange(insertUiEvent.copy(alamat = it)) },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.nomor_telepon,
            onValueChange = { onValueChange(insertUiEvent.copy(nomor_telepon = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            label = { Text("Nomor Telepon") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        println(insertUiEvent.nomor_telepon)
        OutlinedTextField(
            value = insertUiEvent.tanggal_lahir,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggal_lahir = it)) },
            label = { Text("Tanggal Lahir (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        println(insertUiEvent.tanggal_lahir)
        OutlinedTextField(
            value = insertUiEvent.riwayat_medikal,
            onValueChange = { onValueChange(insertUiEvent.copy(riwayat_medikal = it)) },
            label = { Text("Riwayat Medikal") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        println(insertUiEvent.riwayat_medikal)
        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(8.dp)
            )
        }
        Divider(
            thickness = 2.dp,
            modifier = Modifier.padding(5.dp)
        )
    }
}
