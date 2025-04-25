package com.example.homework6.store

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(flight: Favorite)

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun getFlightId ( id: Int ): Flow<Favorite>

    @Query ("SELECT * FROM favorite ORDER BY id ASC")
    fun getAllFavoriteFlights() : Flow<List<Favorite>>

    // add query to search by userInput
    @Query("SELECT * FROM airport WHERE iata_code LIKE :query OR name LIKE :query")
    fun searchFlights(query:String) : Flow<List<Airport>>

    // all flights from specific airport
    @Query("SELECT * FROM airport WHERE iata_code!= :airportCode ORDER BY iata_code ASC")
    fun getFlightsFromAirport(airportCode : String): Flow<List<Airport>>

    @Query("""
        SELECT f.departure_code AS departureCode, origin.name as departureName, f.destination_code AS destinationCode, destiny.name AS destinationName
        FROM favorite f
        JOIN airport AS origin ON f.departure_code= origin.iata_code
        JOIN airport AS destiny ON f.destination_code=destiny.iata_code
    """)
    fun getAllStoredFavorites(): Flow<List<FavoriteStorage>>

    @Query("DELETE FROM favorite")
    suspend fun clearFavorites()
}