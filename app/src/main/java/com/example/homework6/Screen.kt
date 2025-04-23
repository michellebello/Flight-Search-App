@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.homework6

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homework6.repository.FlightModel

val blue = Color(0XFF637ec7)
val ivory = Color(0XFFf6f7f0)

@Composable
fun Screen(viewModel: FlightModel = viewModel(factory = FlightModel.factory)) {
    val favorites by viewModel.getFavorites().collectAsState(emptyList())
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = blue,
                    titleContentColor = ivory
                ),
                title = {
                    Text("Flight Search")
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(favorites) { favorite->
                Text(
                    text = "${favorite.departureCode} ➡ ${favorite.destinationCode}",
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                )
            }
        }
    }
}