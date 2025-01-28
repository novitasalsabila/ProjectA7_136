package com.example.klinik.ui.view.SesiTerapis

import com.example.klinik.ui.view.Pasien.DestinasiDetailPasien

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.klinik.ui.PenyediaViewModel
import com.example.klinik.ui.navigation.DestinasiNavigasi
import com.example.klinik.ui.viewmodel.sesi.InsertSesiUiEvent
import com.example.klinik.ui.viewmodel.sesi.InsertSesiUiState
import com.example.klinik.ui.viewmodel.sesi.InsertSesiViewModel
import kotlinx.coroutines.launch

object DestinasiSesiEntry : DestinasiNavigasi {
    override val route = "Sesi"
    override val titleRes = "Entry Sesi"
    const val ID_SESI = "id_sesi"
    val routeWithArgs = "${DestinasiDetailSesiTerapi.route}/{$ID_SESI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySesiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: InsertSesiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.uiState

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Tambah Sesi Terapi",
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
        EntrySesiBody(
            viewModel = viewModel,
            onSaveClick = {
                updateEvent -> viewModel.updateInsertSesiState(updateEvent)
            },
            uiState = uiState,
            onClick = {
                coroutineScope.launch {
                    viewModel.insertSesi()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
fun EntrySesiBody(
    viewModel: InsertSesiViewModel,
    onSaveClick: (InsertSesiUiEvent) -> Unit,
    onClick: ()->Unit,
    uiState: InsertSesiUiState,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        InsertSesiScreen(
            viewModel = viewModel,
            insertSesiUiEvent = uiState.insertUiEvent,
            onValueChange = onSaveClick
        )

        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun InsertSesiScreen(
    insertSesiUiEvent: InsertSesiUiEvent,
    onValueChange: (InsertSesiUiEvent) ->Unit,
    viewModel: InsertSesiViewModel,
    modifier: Modifier = Modifier) {
    val pasienList = viewModel.pasienList
    val terapisList = viewModel.terapisList
    val jenisTerapiList = viewModel.jenisTerapiList

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Input for ID Sesi
        OutlinedTextField(
            value = insertSesiUiEvent.id_sesi,
            onValueChange = { newIdSesi ->
                onValueChange(insertSesiUiEvent.copy(id_sesi = newIdSesi))
            },
            label = { Text("ID Sesi") },
            modifier = Modifier.fillMaxWidth()
        )


        // Dropdown for Pilih Pasien
        DropdownSelected(
            selectedValue = pasienList.find { it.id_pasien ==  insertSesiUiEvent.id_pasien}?.nama_pasien ?: "Pilih Pasien",
            options = pasienList.map { it.nama_pasien },
            label = "Pilih Pasien",
            onValueChangedEvent = { pasien ->
                val selectedPasien = pasienList.find { it.nama_pasien == pasien }
                selectedPasien?.let {
                    onValueChange(insertSesiUiEvent.copy(id_pasien = it.id_pasien))
                }
            }
        )

        DropdownSelected(
            selectedValue = terapisList.find { it.id_terapis ==  insertSesiUiEvent.id_terapis}?.nama_terapis?:"Pilih Terapis",
            options = terapisList.map { it.nama_terapis },
            label = "Pilih Terapis",
            onValueChangedEvent = {
                    terapis ->
                val selectedTerapis = terapisList.find { it.nama_terapis == terapis }
                selectedTerapis?.let {
                    onValueChange(insertSesiUiEvent.copy(id_terapis = it.id_terapis))
                }
            }
        )
        DropdownSelected(
            selectedValue = jenisTerapiList.find { it.id_jenis_terapi ==  insertSesiUiEvent.id_jenis_terapi}?.nama_jenis_terapi?:"Pilih Jenis Terapi",
            options = jenisTerapiList.map { it.nama_jenis_terapi },
            label = "Pilih Jenis Terapi",
            onValueChangedEvent = {
                    jenisTerapi ->
                val selectedJenisTerapi = jenisTerapiList.find { it.nama_jenis_terapi == jenisTerapi }
                selectedJenisTerapi?.let {
                    onValueChange(insertSesiUiEvent.copy(id_jenis_terapi = it.id_jenis_terapi))
                }
            }
        )

        // Input for Tanggal Sesi
        OutlinedTextField(
            value = insertSesiUiEvent.tanggal_sesi,
            onValueChange = { newTanggalSesi ->
                onValueChange(insertSesiUiEvent.copy(tanggal_sesi = newTanggalSesi))
            },
            label = { Text("Tanggal Sesi") },
            modifier = Modifier.fillMaxWidth()
        )

        // Input for Catatan Sesi
        OutlinedTextField(
            value = insertSesiUiEvent.catatan_sesi,
            onValueChange = { newCatatanSesi ->
                onValueChange(insertSesiUiEvent.copy(catatan_sesi = newCatatanSesi))
            },
            label = { Text("Catatan Sesi") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelected(
    selectedValue: String,
    options: List<String>,
    label: String,
    onValueChangedEvent: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }, modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true, value = selectedValue,
            onValueChange = {}, label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            }, colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded,
            onDismissRequest = { expanded = false }) {
            options.forEach { option: String ->
                DropdownMenuItem(text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    }
                )
            }
        }
    }
}
