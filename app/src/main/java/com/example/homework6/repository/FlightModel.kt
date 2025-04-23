package com.example.homework6.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.homework6.store.Favorite
import kotlinx.coroutines.launch

class FlightModel(private val flightRepository: FlightRepository) : ViewModel() {
    val favorites: LiveData<List<Favorite>> = flightRepository.favoriteFlights.asLiveData()
    fun addFavorite (flight: Favorite) = viewModelScope.launch { flightRepository.addFavoriteFlight(flight) }
}