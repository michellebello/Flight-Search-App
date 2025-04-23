package com.example.homework6.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.homework6.FlightApplication
import com.example.homework6.store.Favorite
import com.example.homework6.store.FlightDao
import kotlinx.coroutines.flow.Flow

class FlightModel(private val flightDao: FlightDao) : ViewModel() {
    fun getFavorites() : Flow<List<Favorite>> = flightDao.getAllFavoriteFlights()

    suspend fun addFavorite(favorite: Favorite) {
        flightDao.insert(favorite)
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightApplication)
                FlightModel(application.database.flightDao())
            }
        }
    }

}