package com.example.klinik.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.klinik.KlinikApplications
import com.example.klinik.ui.viewmodel.pasien.DetailPasienViewModel
import com.example.klinik.ui.viewmodel.pasien.HomePasienViewModel
import com.example.klinik.ui.viewmodel.pasien.InsertPasienViewModel
import com.example.klinik.ui.viewmodel.JenisTerapi.DetailJenisTerapiViewModel
import com.example.klinik.ui.viewmodel.JenisTerapi.HomeJenisTerapiViewModel
import com.example.klinik.ui.viewmodel.JenisTerapi.InsertJenisTerapiViewModel
import com.example.klinik.ui.viewmodel.JenisTerapi.UpdateJenisTerapiViewModel
import com.example.klinik.ui.viewmodel.pasien.UpdatePasienViewModel
import com.example.klinik.ui.viewmodel.sesi.DetailSesiTerapiViewModel
import com.example.klinik.ui.viewmodel.sesi.HomeSesiTerapiViewModel
import com.example.klinik.ui.viewmodel.sesi.InsertSesiViewModel
import com.example.klinik.ui.viewmodel.sesi.UpdateSesiTerapiViewModel
import com.example.klinik.ui.viewmodel.terapisVM.DetailTerapisViewModel
import com.example.klinik.ui.viewmodel.terapisVM.HomeTerapisViewModel
import com.example.klinik.ui.viewmodel.terapisVM.InsertTerapisViewModel
import com.example.klinik.ui.viewmodel.terapisVM.UpdateTerapisViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomePasienViewModel(klinikApp().container.pasienRepository) }
        initializer { InsertPasienViewModel(klinikApp().container.pasienRepository) }
        initializer { UpdatePasienViewModel(createSavedStateHandle(),klinikApp().container.pasienRepository) }
        initializer { DetailPasienViewModel(createSavedStateHandle(),klinikApp().container.pasienRepository) }
//        initializer { UpdatePasienViewModel(createSavedStateHandle(), klinikApp().container.pasienRepository)}
        initializer{ HomeTerapisViewModel(klinikApp().container.terapisRepository)}
        initializer{ InsertTerapisViewModel(klinikApp().container.terapisRepository)}
        initializer { DetailTerapisViewModel(createSavedStateHandle(), klinikApp().container.terapisRepository) }
        initializer { UpdateTerapisViewModel(createSavedStateHandle(), klinikApp().container.terapisRepository) }

        initializer{ HomeJenisTerapiViewModel(klinikApp().container.jenisTerapiRepository)}
        initializer{ DetailJenisTerapiViewModel(createSavedStateHandle(),klinikApp().container.jenisTerapiRepository) }
        initializer{ InsertJenisTerapiViewModel(klinikApp().container.jenisTerapiRepository)}
        initializer{ UpdateJenisTerapiViewModel(createSavedStateHandle(),klinikApp().container.jenisTerapiRepository) }

        initializer { HomeSesiTerapiViewModel(klinikApp().container.sesiTerapiRepository) }
        initializer { InsertSesiViewModel(klinikApp().container.sesiTerapiRepository, klinikApp().container.pasienRepository, klinikApp().container.terapisRepository, klinikApp().container.jenisTerapiRepository) }
        initializer { DetailSesiTerapiViewModel(createSavedStateHandle(),klinikApp().container.sesiTerapiRepository) }
        initializer { UpdateSesiTerapiViewModel(createSavedStateHandle(), klinikApp().container.sesiTerapiRepository, klinikApp().container.pasienRepository, klinikApp().container.terapisRepository, klinikApp().container.jenisTerapiRepository) }

    }
}

fun CreationExtras.klinikApp(): KlinikApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KlinikApplications)
