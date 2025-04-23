package com.example.homework6.store

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")
data class Airport (
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "iata_code")
    val airportCode: String,
    @ColumnInfo(name = "name")
    val airportName: String,
    @ColumnInfo(name = "passengers")
    val passengers: Int
)

@Entity(tableName = "favorite")
data class Favorite (
    @PrimaryKey
    val id: Int,
    @ColumnInfo (name="departure_code")
    val departureCode: String,
    @ColumnInfo (name="destination_code")
    val destinationCode: String
)