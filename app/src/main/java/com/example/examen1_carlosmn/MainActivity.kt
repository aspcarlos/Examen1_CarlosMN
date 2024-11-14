package com.example.examen1_carlosmn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navegacion()
        }
    }
}

@Composable
fun Navegacion() {
    // Crea y recuerda un controlador de navegación
    val navController = rememberNavController()
    // Define un NavHost que administra las rutas y destinos de navegación
    NavHost(
        navController = navController, // Controlador de navegación
        startDestination = "pantallaListado" // Pantalla inicial que se mostrará al abrir la app
    ) {
        // Define un destino de navegación para la pantalla de listado de vehiculos
        composable("pantallaListado") {
            // Llama al Composable ListadoVehiculos
            ListadoVehiculos(navController)
        }
        // Define un destino de navegación para la pantalla de detalle del parque
        composable("pantallaDetalle/{nombreVehiculos}") { backStackEntry ->
            // Extrae el parámetro "nombreParque" de los argumentos de navegación
            val nombreParque = backStackEntry.arguments?.getString("nombreParque")
            // Llama al Composable PantallaDetalle, pasando el controlador de navegación y el nombre del parque
            PantallaDetalle(navController, nombreParque)
        }
    }
}