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
}