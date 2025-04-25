@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.homework6

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homework6.repository.FlightModel
import com.example.homework6.store.Airport
import kotlinx.coroutines.flow.flowOf
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.font.FontWeight

val blue = Color(0XFF637ec7)
val ivory = Color(0XFFf6f7f0)
val gray = Color(0XFFb0b3b8)

@Composable
fun Screen(viewModel: FlightModel = viewModel(factory = FlightModel.factory)) {
    val favorites by viewModel.getStoredFavorites().collectAsState(emptyList())
    var userInput by remember { mutableStateOf("") }
    val flights by viewModel.searchFlightsByAirport(userInput).collectAsState(initial= emptyList())
    var selectedAirport: Airport? by remember { mutableStateOf(null) }

    val flightsFromSelectedAirport by remember(selectedAirport) {
        if(selectedAirport != null) {
            viewModel.getFlightsFromAirport(selectedAirport!!.airportCode)
        } else {
            flowOf(emptyList())
        }
    }.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = blue,
                    titleContentColor = ivory
                ),
                title = {
                    Text("Flight Search", modifier = Modifier.padding(10.dp))
                }
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextField(
                value = userInput,
                onValueChange = {userInput=it},
                label = {Text("Type an airport name or code")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                userInput.isBlank() -> {
                    Column(
                        Modifier.fillMaxWidth()
                    ){
                        Text("Favorite routes", modifier = Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
                        LazyColumn {
                            items(favorites) { favorite ->
                                FlightInfoCard(
                                    originCode = favorite.departureCode,
                                    originName = favorite.departureName,
                                    destinationCode = favorite.destinationCode,
                                    destinationName = favorite.destinationName,
                                    onAddFavorite = {}
                                )
                            }
                        }
                    }

                }
                selectedAirport == null -> {
                    LazyColumn(
                        contentPadding = innerPadding,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(flights) { airport ->
                            Text(
                                text = "${airport.airportCode} ➡ ${airport.airportName}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                                    .clickable {
                                        selectedAirport = airport
                                        userInput = airport.airportCode
                                    }
                            )
                        }
                    }
                }
                else  -> {
                        LazyColumn(
                            contentPadding = innerPadding,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(flightsFromSelectedAirport) { flight ->
                               FlightInfoCard(
                                   originCode = selectedAirport!!.airportCode,
                                   originName = selectedAirport!!.airportName,
                                   destinationCode = flight.airportCode,
                                   destinationName = flight.airportName,
                                   onAddFavorite = {
                                       viewModel.addFavoriteFlight(
                                           selectedAirport!!.airportCode,
                                           flight.airportCode
                                       )
                                   }
                               )
                            }
                        }
                    }
            }

        }

    }
}

@Composable
fun FlightInfoCard(originCode: String, originName: String, destinationCode: String, destinationName: String, onAddFavorite: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = gray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
    {
        Row(
            Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "DEPART")
                Row {
                    Text(text = originCode, fontWeight = FontWeight.ExtraBold)
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = originName)
                }
                Text(text = "ARRIVE")
                Row {
                    Text(text = destinationCode, fontWeight = FontWeight.ExtraBold)
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = destinationName)
                }
            }
            IconButton(onClick = onAddFavorite) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Save to favorites",
                    tint = blue
                )
            }
        }
    }
}