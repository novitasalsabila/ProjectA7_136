package com.example.klinik.ui.view.Pasien


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinik.ui.PenyediaViewModel
import com.example.klinik.ui.customwidget.TopAppBar
import com.example.klinik.ui.navigation.DestinasiNavigasi
import com.example.klinik.ui.viewmodel.pasien.UpdatePasienViewModel
import kotlinx.coroutines.launch

object DestinasiEditPasien : DestinasiNavigasi {
    override val route = "pasien_edit"
    override val titleRes = "Edit Pasien"
    const val ID_PASIEN = "id_pasien"
    val routeWithArgs = "$route/{$ID_PASIEN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePasienView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePasienViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val insertPasienUiState = viewModel.updateUiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiEditPasien.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryPasienBody(
            insertUiState = insertPasienUiState,
            onPasienValueChange = { viewModel.updateInsertPasienState(it) },
            onSaveClick = {  
                coroutineScope.launch {
                    viewModel.updatePasien()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}