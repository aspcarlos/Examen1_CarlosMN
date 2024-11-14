package com.example.examen1_carlosmn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Composable que muestra la pantalla de detalles de un parque con el nombre del parque
// y un botón para retroceder a la pantalla de listado.
@Composable
fun PantallaDetalle(navController: NavController, nombreVehiculo: String?) {
    // Column: Componente que organiza los elementos de manera vertical
    Column(
        modifier = Modifier
            .fillMaxSize() // Hace que la columna ocupe todo el tamaño disponible en la pantalla
            .padding(16.dp) // Añade un espacio de 16dp alrededor de los elementos dentro de la columna
    ) {
        // Text: Muestra el nombre del parque recibido como argumento en 'nombreParque'
        // Si 'nombreParque' es null, se mostrará 'null' como texto
        Text(
            text = "Pantalla de detalle del vehiculo: $nombreVehiculo", // El texto mostrará el nombre del vehiculo
            fontSize = 20.sp // Tamaño de la fuente del texto, en este caso 20sp
        )

        // Button: Un botón que, al ser presionado, navega hacia la pantalla de listado
        Button(onClick = {
            // Acción al hacer clic en el botón: navega hacia la pantalla "pantallaListado"
            navController.navigate("pantallaListado")
        }) {
            // Texto dentro del botón que se verá en la pantalla
            Text(text = "Retroceder") // El texto del botón indica que al presionar, retrocederá a la pantalla de listado
        }
    }
}