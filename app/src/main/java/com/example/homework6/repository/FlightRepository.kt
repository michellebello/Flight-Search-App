package com.example.homework6.repository

import com.example.homework6.store.Favorite
import com.example.homework6.store.FlightDao
import kotlinx.coroutines.flow.Flow

class FlightRepository (private val flightDao: FlightDao){
    val favoriteFlights: Flow<List<Favorite>> = flightDao.getAllFavoriteFlights()

    suspend fun addFavoriteFlight(favorite: Favorite) {
        flightDao.insert(favorite)
    }

    fun getFavoriteFlightById(id: Int): Flow<Favorite> = flightDao.getFlightId(id)
}