package com.example.homework6

import android.app.Application
import com.example.homework6.store.FlightDatabase

class FlightApplication : Application() {
    val database: FlightDatabase by lazy {FlightDatabase.getDatabase(this)}
}