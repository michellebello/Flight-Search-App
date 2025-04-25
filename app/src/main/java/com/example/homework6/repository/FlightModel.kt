package com.example.homework6.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.homework6.FlightApplication
import com.example.homework6.store.Airport
import com.example.homework6.store.Favorite
import com.example.homework6.store.FlightDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FlightModel(private val flightDao: FlightDao) : ViewModel() {
    fun getFavorites() : Flow<List<Favorite>> = flightDao.getAllFavoriteFlights()

    // add method to searchBy user input in screens.kt
    fun searchFlightsByAirport(query: String): Flow<List<Airport>> {
        return flightDao.searchFlights("%${query}")
    }

    // add method to get flights from specific airport selected
    fun getFlightsFromAirport(airportCode : String) : Flow<List<Airport>> {
        return flightDao.getFlightsFromAirport(airportCode)
    }

    fun addFavoriteFlight(airportCode: String, destinyCode: String){
        viewModelScope.launch {
            val favorite = Favorite(
                departureCode = airportCode,
                destinationCode = destinyCode
            )
            flightDao.insert(favorite)
        }
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