package com.example.exsergio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contratartareas.data.DataSource
import com.example.contratartareas.data.Tarea
import com.example.exsergio.ui.theme.ExSergioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExSergioTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExamenSergio()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamenSergio(
    modifier: Modifier = Modifier,
    tareasTotales: ArrayList<Tarea> = DataSource.tareas
) {

    var ultimaAccion by remember { mutableStateOf("Todavia no se ha añadido nada(defecto") }


    var resumen by remember { mutableStateOf("") }

    var textoHoras by remember { mutableStateOf(0) }

    var nombreTarea by remember { mutableStateOf("") }

    var horasContratar by remember { mutableStateOf("") }

    Column(modifier = Modifier) {

        Column(
            modifier = Modifier
                .height(400.dp)
        ) {

            LazyVerticalGrid(
                GridCells.Fixed(3)

            ) {
                items(tareasTotales) { tareas ->

                    Card(
                        modifier = Modifier
                            .padding(5.dp)
                            .height(200.dp)
                    ) {

                        Text(
                            text = "Tarea: ${tareas.nombre}",
                            modifier = Modifier
                                .background(Color.Yellow)
                                .padding(12.dp)
                                .fillMaxWidth()

                        )


                        Text(
                            text = "€ /hora: ${tareas.precio}",
                            modifier = Modifier
                                .background(Color.Cyan)
                                .padding(12.dp)
                                .fillMaxWidth()

                        )

                        Button(
                            onClick = {


                                if (horasContratar.isBlank()) {

                                    horasContratar = "1"
                                }

                                tareas.totalHoras += horasContratar.toInt()



                                ultimaAccion = "Se añade $horasContratar hora a ${tareas.nombre}"





                                resumen = buildString {

                                    tareasTotales
                                        .filter { it.totalHoras >= 1 }
                                        .forEach { tareas ->

                                            append("La tarea: ${tareas.nombre} Precio: ${tareas.precio} Horas: ${tareas.totalHoras} \n")
                                            textoHoras = tareas.totalHoras
                                        }


                                }


                            }, modifier = Modifier
                                .align(Alignment.CenterHorizontally)


                        ) {


                            Text(text = "+")

                        }

                    }
                }
            }
        }

        Row(modifier = Modifier) {


            TextField(
                value = nombreTarea,
                onValueChange = { nombreTarea = it },
                label = { Text(text = "Nombre nueva tarea:") },
                modifier = Modifier

                    .padding(20.dp)
                    .width(220.dp)
            )

            Button(
                onClick = {
                    var existe = false

                    if (nombreTarea.isBlank()) {


                        ultimaAccion = "Se encuentra en blanco"
                    } else {


                        for (t in tareasTotales) {


                            if (t.nombre.equals(nombreTarea, ignoreCase = true)) {
                                existe = true
                                ultimaAccion = "Ya existe"
                            }


                        }


                        if (!existe) {

                            ultimaAccion = "Tarea añadida"

                            tareasTotales.add(Tarea(nombreTarea, 10, 0))
                        }
                    }


                }, modifier = Modifier
                    .padding(15.dp)


            ) {


                Text(text = "Nueva tarea")

            }


        }


        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .height(250.dp)

        ) {

            Column(modifier = Modifier.padding(30.dp)) {

                Text(
                    text = "Ultima Accion: \n$ultimaAccion",
                    modifier = Modifier
                        .background(Color.Yellow)
                        .fillMaxWidth()
                )

                Text(
                    text = "Resumen: \n$resumen",
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .height(120.dp)
                )

                Text(
                    text = "Total horas: \n$textoHoras",
                    modifier = Modifier
                        .background(Color.Yellow)
                        .fillMaxWidth()
                )

            }

        }


    }
}

