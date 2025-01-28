package com.example.klinik.ui.view.SesiTerapis

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinik.model.Pasien
import com.example.klinik.model.Terapis
import com.example.klinik.model.JenisTerapi
import com.example.klinik.ui.PenyediaViewModel
import com.example.klinik.ui.navigation.DestinasiNavigasi
import com.example.klinik.ui.viewmodel.sesi.InsertSesiUiEvent
import com.example.klinik.ui.viewmodel.sesi.InsertSesiUiState
import com.example.klinik.ui.viewmodel.sesi.UpdateSesiTerapiViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateSesiTerapi : DestinasiNavigasi {
    override val route = "update sesi"
    const val ID_SESI = "id_sesi"
    override val titleRes = "Detail Sesi"
    val routeWithArg = "$route/{$ID_SESI}"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SesiTerapiUpdateView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateSesiTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val sesiTerapiUiState = viewModel.uiState
    val pasienList = viewModel.pasienList
    val terapisList = viewModel.terapisList
    val jenisTerapiList = viewModel.jenisTerapiList
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Update Sesi Terapi",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        EntrySesiTerapiBody(
            uiState = sesiTerapiUiState,
            onSesiTerapiValueChange = { viewModel.updateInsertSesiTerapiState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateSesi()
                    navigateBack()
                }
            },
            pasienList = pasienList,
            terapisList = terapisList,
            jenisTerapiList = jenisTerapiList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}


@Composable
fun EntrySesiTerapiBody(
    uiState: InsertSesiUiState,
    onSesiTerapiValueChange: (InsertSesiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    pasienList: List<Pasien>,
    terapisList: List<Terapis>,
    jenisTerapiList: List<JenisTerapi>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Pasien Dropdown
        DropdownField(
            label = "Pilih Pasien",
            options = pasienList.map { it.nama_pasien },
            selectedValue = pasienList.find { it.id_pasien == uiState.insertUiEvent.id_pasien }?.nama_pasien ?: "Pilih Pasien",
            onValueChangedEvent = { pasienName ->
                val selectedPasien = pasienList.find { it.nama_pasien == pasienName }
                selectedPasien?.let {
                    onSesiTerapiValueChange(uiState.insertUiEvent.copy(id_pasien = it.id_pasien))
                }
            }
        )

        // Terapis Dropdown
        DropdownField(
            label = "Pilih Terapis",
            options = terapisList.map { it.nama_terapis },
            selectedValue = terapisList.find { it.id_terapis == uiState.insertUiEvent.id_terapis }?.nama_terapis ?: "Pilih Terapis",
            onValueChangedEvent = { terapisName ->
                val selectedTerapis = terapisList.find { it.nama_terapis == terapisName }
                selectedTerapis?.let {
                    onSesiTerapiValueChange(uiState.insertUiEvent.copy(id_terapis = it.id_terapis))
                }
            }
        )

        // Jenis Terapi Dropdown
        DropdownField(
            label = "Pilih Jenis Terapi",
            options = jenisTerapiList.map { it.nama_jenis_terapi },
            selectedValue = jenisTerapiList.find { it.id_jenis_terapi == uiState.insertUiEvent.id_jenis_terapi }?.nama_jenis_terapi ?: "Pilih Jenis Terapi",
            onValueChangedEvent = { jenisTerapiName ->
                val selectedJenisTerapi = jenisTerapiList.find { it.nama_jenis_terapi == jenisTerapiName }
                selectedJenisTerapi?.let {
                    onSesiTerapiValueChange(uiState.insertUiEvent.copy(id_jenis_terapi = it.id_jenis_terapi))
                }
            }
        )

        // Tanggal Sesi
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Tanggal Sesi")
            OutlinedTextField(
                value = uiState.insertUiEvent.tanggal_sesi,
                onValueChange = {
                    onSesiTerapiValueChange(uiState.insertUiEvent.copy(tanggal_sesi = it))
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Tanggal Sesi") }
            )
        }

        // Catatan Sesi
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Catatan Sesi")
            OutlinedTextField(
                value = uiState.insertUiEvent.catatan_sesi,
                onValueChange = {
                    onSesiTerapiValueChange(uiState.insertUiEvent.copy(catatan_sesi = it))
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Catatan Sesi") }
            )
        }

        // Button Simpan
        Button(onClick = onSaveClick, modifier = Modifier.padding(top = 16.dp)) {
            Text("Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedValue: String,
    onValueChangedEvent: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(label)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedValue,
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            expanded = false
                            onValueChangedEvent(option)
                        }
                    )
                }
            }
        }
    }
}
