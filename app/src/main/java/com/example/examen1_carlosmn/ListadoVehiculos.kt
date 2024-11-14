package com.example.examen1_carlosmn

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun ListadoVehiculos(navController: NavHostController) {
    var textoCuadro by rememberSaveable { mutableStateOf("") }
    var vehiculosfiltrados = vehiculos.filter { it.nombre.contains(textoCuadro, ignoreCase = true) }

    val elementos = listOf(
        ElementoMenu("Inicio", Icons.Filled.Home, Icons.Outlined.Home),
        ElementoMenu("Favoritos", Icons.Filled.Favorite, Icons.Outlined.Favorite),
        ElementoMenu("Cerrar Sesion", Icons.Filled.Close, Icons.Outlined.Close)
    )

    val drawerState = rememberDrawerState(DrawerValue.Closed) // Crea y recuerda el estado del menú lateral (cerrado inicialmente).
    val selectedItem = remember { mutableStateOf(elementos[0]) } // Guarda el elemento seleccionado del menú lateral.
    val scope = rememberCoroutineScope() // Crea un alcance para manejar corrutinas en este componente.

    ModalNavigationDrawer( // Componente que permite crear un menú lateral con contenido y opciones.
        drawerContent = { // Define el contenido del menú lateral.
            ModalDrawerSheet( // Contenedor del menú lateral.
                modifier = Modifier // Modificador que define propiedades visuales del contenedor.
                    .width(300.dp) // Ancho fijo de 300dp para el menú lateral.
                    .fillMaxHeight() // Ocupa todo el alto disponible.
                    .background(Color.White) // Fondo blanco para el menú.
            ) {
                Spacer(modifier = Modifier.height(40.dp)) // Espacio inicial en la parte superior.
                elementos.forEach { item -> // Itera por cada elemento del menú.
                    NavigationDrawerItem( // Crea un ítem en el menú lateral.
                        icon = { Icon(item.iconoNoSeleccionado, contentDescription = "") }, // Ícono del elemento.
                        label = { Text(text = item.titulo) }, // Texto del elemento.
                        selected = item == selectedItem.value, // Marca el elemento como seleccionado si coincide.
                        onClick = { // Define qué hacer al hacer clic.
                            selectedItem.value = item // Cambia el elemento seleccionado.
                            scope.launch { // Lanza una corrutina.
                                if (drawerState.isOpen) drawerState.close() // Si está abierto, cierra el menú.
                                else drawerState.open() // Si está cerrado, abre el menú.
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding) // Aplica padding al ítem.
                    )
                }
            }
        },
        content = { // Contenido principal que se muestra fuera del menú lateral.
            Scaffold( // Define la estructura de la pantalla con barras superior e inferior.
                topBar = { MyTopBar(onClickDrawer = { scope.launch { drawerState.open() } }) }, // Barra superior con botón para abrir el menú lateral.
                bottomBar = { MyBottomBar() }, // Barra inferior personalizada.
                content = { padding -> // Contenido principal de la pantalla.
                    Column( // Organiza elementos en una columna.
                        modifier = Modifier // Modificador para configurar la columna.
                            .fillMaxSize() // Ocupa todo el espacio disponible.
                            .padding(top = 150.dp, start = 16.dp, end = 16.dp), // Agrega espacio alrededor del contenido.
                        horizontalAlignment = Alignment.CenterHorizontally // Alinea elementos horizontalmente al centro.
                    ) {
                        cuadroBuscar(textoCuadro) { textoCuadro = it } // Muestra un cuadro de búsqueda que actualiza el texto ingresado.
                        listadoVehiculos(vehiculosfiltrados, navController) // Lista los parques filtrados según la búsqueda.
                    }
                }
            )
        }
    )
}

val vehiculos = listOf(
    Vehiculos("Carrozado con trampilla", R.drawable.carrozado, "20m3", "120€/dia"),
    Vehiculos("Carrozado sin trampilla", R.drawable.grande, "20m3", "110€/dia"),
    Vehiculos("Camioneta mediana", R.drawable.mediana, "20m3", "100€/dia"),
    Vehiculos("Camioneta pequeña", R.drawable.pequenia, "20m3", "90€/dia")
)

// Composable de la lista de parques
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun listadoVehiculos(vehiculosfiltrados: List<Vehiculos>, navController: NavController) {
    val contexto = LocalContext.current // Obtiene el contexto de la aplicación para mostrar Toasts.
    LazyColumn(modifier = Modifier.fillMaxWidth()) { // Columna con desplazamiento vertical.
        vehiculosfiltrados.forEach { vehiculo -> // Itera sobre los parques filtrados.
            item { // Define cada elemento de la lista.
                ElevatedCard( // Tarjeta elevada para mostrar información del parque.
                    onClick = { // Acción al hacer clic en la tarjeta.
                        navController.navigate("pantallaDetalle/${vehiculo.nombre}") // Navega a la pantalla de detalles del vehiculo.
                        Toast.makeText( // Muestra un mensaje temporal en la pantalla.
                            contexto, "${vehiculo.nombre} pulsado", Toast.LENGTH_LONG
                        ).show()
                    },
                    modifier = Modifier // Modificador para configurar la tarjeta.
                        .padding(8.dp) // Agrega espacio alrededor de la tarjeta.
                        .fillMaxWidth(), // Hace que la tarjeta ocupe todo el ancho disponible.
                    elevation = CardDefaults.elevatedCardElevation(16.dp), // Define la elevación visual de la tarjeta.
                    colors = CardDefaults.cardColors(containerColor = Color.White) // Color de fondo blanco para la tarjeta.
                ) {
                    Column( // Contenedor vertical para organizar los elementos uno debajo del otro.
                        modifier = Modifier.padding(16.dp), // Agrega espacio alrededor del contenido.
                        horizontalAlignment = Alignment.CenterHorizontally, // Centra los elementos horizontalmente.
                        verticalArrangement = Arrangement.Center // Centra los elementos verticalmente.
                    ) {
                        Image( // Imagen del vehículo.
                            painter = painterResource(id = vehiculo.imagen), // Carga la imagen desde los recursos.
                            contentDescription = vehiculo.nombre, // Texto alternativo para accesibilidad.
                            contentScale = ContentScale.Crop, // Ajusta la imagen para que ocupe el espacio disponible.
                            modifier = Modifier // Modificador para configurar la imagen.
                                .clip(RoundedCornerShape(8.dp)) // Aplica esquinas redondeadas.
                                .size(120.dp) // Define el tamaño de la imagen.
                        )
                        Text(
                            text = vehiculo.nombre, // Texto con el nombre del vehículo.
                            fontWeight = FontWeight.Bold, // Texto en negrita.
                            modifier = Modifier.padding(top = 8.dp) // Agrega espacio entre la imagen y el texto.
                        )
                        Text(
                            text = vehiculo.capacidad, // Texto con la capacidad del vehículo.
                            modifier = Modifier.padding(top = 8.dp) // Agrega espacio entre los textos.
                        )
                        Text(
                            text = vehiculo.precio, // Texto con el precio del vehículo.
                            fontWeight = FontWeight.Bold, // Texto en negrita.
                            modifier = Modifier.padding(top = 8.dp) // Agrega espacio entre los textos.
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(onClickDrawer: () -> Unit) {
    // Crea una barra superior
    TopAppBar(
        colors = topAppBarColors(
            containerColor = Color.Gray, // Color de fondo de la barra.
            titleContentColor = Color.White // Color del título.
        ),
        title = {
            // Define el contenido del título de la barra.
            Text(text = "Alquiler de Vehiculos") // Título visible en la barra superior.
        },
        navigationIcon = {
            // Ícono de navegación
            IconButton(onClick = { onClickDrawer() }) {
                // Botón que ejecuta la función proporcionada al hacer clic.
                Icon(
                    imageVector = Icons.Filled.Menu, // Ícono de menú.
                    contentDescription = "",
                    tint = Color.White // Color del ícono.
                )
            }
        },
        actions = {
            // Contenedor de acciones adicionales en la barra superior.
            IconButton(onClick = { /*TODO*/ }) {
                // Botón para buscar.
                Icon(
                    imageVector = Icons.Filled.AccountCircle, // Ícono de cuenta de usuario.
                    contentDescription = "",
                    tint = Color.White // Color del ícono.
                )
            }
        }
    )
}

// Composable para el cuadro de búsqueda
@Composable // Indica que esta función es un componente visual.
fun cuadroBuscar(textoCuadro: String, textoCambiado: (String) -> Unit) {
    TextField( // Campo de texto editable.
        modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho disponible.
        value = textoCuadro, // Contenido actual del cuadro de texto.
        onValueChange = { textoCambiado(it) }, // Callback que se ejecuta al cambiar el texto.
        placeholder = { Text(text = "Buscar") }, // Texto de marcador de posición.
        leadingIcon = { // Ícono que aparece al principio del cuadro.
            Icon(
                imageVector = Icons.Filled.Search, // Ícono de búsqueda predeterminado.
                contentDescription = "", // Descripción accesible del ícono (vacía en este caso).
                tint = Color(0xFF696464) // Color gris para el ícono.
            )
        },
        colors = TextFieldDefaults.colors( // Define los colores del cuadro de texto.
            unfocusedContainerColor = Color.White, // Color cuando no está enfocado.
            focusedContainerColor = Color.White // Color cuando está enfocado.
        )
    )
}

// Barra Inferior con los iconos de inicio, favoritos.
@Composable
fun MyBottomBar() {
    NavigationBar(containerColor = Color.Gray) {
        // Primer elemento de la barra de navegación: "Inicio".
        NavigationBarItem(
            selected = true, // Indica que este elemento está seleccionado actualmente.
            onClick = { /*TODO*/ }, // Acción que se ejecuta al hacer clic en el elemento.
            icon = { // Ícono del elemento.
                Icon(
                    imageVector = Icons.Filled.Home, // Usa el ícono de "Home".
                    contentDescription = ""
                )
            },
            label = {
                // Etiqueta del elemento.
                Text(
                    text = "Inicio", // Texto que aparece como etiqueta.
                    color = Color.White // Color blanco para el texto.
                )
            },
            colors = NavigationBarItemDefaults.colors(Color.White) // Define el color para el ícono y el texto seleccionados.
        )

        // Segundo elemento de la barra de navegación: "Favoritos".
        NavigationBarItem(
            selected = true, // Indica que este elemento también está seleccionado.
            onClick = { /*TODO*/ }, // Acción que se ejecuta al hacer clic.
            icon = {
                // Ícono del elemento.
                Icon(
                    imageVector = Icons.Filled.Favorite, // Usa el ícono de "Favoritos".
                    contentDescription = "" // Descripción accesible vacía.
                )
            },
            label = {
                // Etiqueta del elemento.
                Text(
                    text = "Favoritos", // Texto que aparece como etiqueta.
                    color = Color.White // Color blanco para el texto.
                )
            },
            colors = NavigationBarItemDefaults.colors(Color.White) // Define los colores seleccionados.
        )
    }
}