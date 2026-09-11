package com.example.formulario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    // Estados de los campos
    var nombre by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var asignatura by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    // Estados para los errores
    var errorNombre by remember { mutableStateOf(false) }
    var errorMatricula by remember { mutableStateOf(false) }
    var errorAsignatura by remember { mutableStateOf(false) }
    var errorHora by remember { mutableStateOf(false) }
    var errorFecha by remember { mutableStateOf(false) }
    var mostrarTarjeta by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .safeDrawingPadding()
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ingresa los datos solicitados",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

            // CAMPO: NOMBRE (Solo letras y espacios)
            TextField(
                value = nombre,
                onValueChange = { nombre = it; errorNombre = false; mostrarTarjeta = false },
                label = { Text("Nombre") },
                isError = errorNombre,
                modifier = Modifier.fillMaxWidth()
            )
            if (errorNombre) {
                Text("Error: Solo se aceptan letras (sin números ni caracteres especiales)",
                    color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Start))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // CAMPO: MATRÍCULA (Solo números)
            TextField(
                value = matricula,
                onValueChange = { matricula = it; errorMatricula = false; mostrarTarjeta = false },
                label = { Text("Matrícula") },
                isError = errorMatricula,
                modifier = Modifier.fillMaxWidth()
            )
            if (errorMatricula) {
                Text("Error: Solo se aceptan números (sin letras o símbolos)",
                    color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Start))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // CAMPO: ASIGNATURA (Letras y números)
            TextField(
                value = asignatura,
                onValueChange = { asignatura = it; errorAsignatura = false; mostrarTarjeta = false },
                label = { Text("Asignatura") },
                isError = errorAsignatura,
                modifier = Modifier.fillMaxWidth()
            )
            if (errorAsignatura) {
                Text("Error: Solo se aceptan letras y números",
                    color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Start))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // CAMPO: HORA
            TextField(
                value = hora,
                onValueChange = { hora = it; errorHora = false; mostrarTarjeta = false },
                label = { Text("Hora (ej. 14:30)") },
                isError = errorHora,
                modifier = Modifier.fillMaxWidth()
            )
            if (errorHora) {
                Text("Error: Formato de hora incorrecto (usa HH:MM)",
                    color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Start))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // CAMPO: FECHA
            TextField(
                value = fecha,
                onValueChange = { fecha = it; errorFecha = false; mostrarTarjeta = false },
                label = { Text("Fecha (ej. 15/10/2024)") },
                isError = errorFecha,
                modifier = Modifier.fillMaxWidth()
            )
            if (errorFecha) {
                Text("Error: Formato de fecha incorrecto (usa DD/MM/AAAA)",
                    color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Start))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // BOTÓN GUARDAR Y VALIDACIÓN
            Button(
                onClick = {
                    // Validaciones usando Regex (Expresiones Regulares)

                    // Solo acepta letras (mayúsculas, minúsculas, acentos, ñ) y espacios
                    val nombreValido = nombre.isNotBlank() && nombre.matches(Regex(
                        "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$"))

                    // Solo acepta dígitos del 0 al 9
                    val matriculaValida = matricula.isNotBlank() && matricula.matches(Regex(
                        "^[0-9]+$"))

                    // Acepta letras, números y espacios
                    val asignaturaValida = asignatura.isNotBlank() && asignatura.matches(Regex(
                        "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]+$"))

                    // Valida formato de hora HH:MM (00:00 a 23:59)
                    val horaValida = hora.matches(Regex("^([01]?[0-9]|2[0-3]):[0-5][0-9]$"))

                    // Valida formato de fecha DD/MM/AAAA
                    val fechaValida = fecha.matches(Regex("^(0[1-9]|[12][0-9]|3[01])/" +
                            "(0[1-9]|1[012])/\\d{4}$"))

                    // Actualizamos los estados de error
                    errorNombre = !nombreValido
                    errorMatricula = !matriculaValida
                    errorAsignatura = !asignaturaValida
                    errorHora = !horaValida
                    errorFecha = !fechaValida

                    // Si todos son válidos, mostramos la tarjeta
                    mostrarTarjeta = nombreValido && matriculaValida && asignaturaValida
                            && horaValida && fechaValida
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (mostrarTarjeta) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Datos Registrados:", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Nombre: $nombre")
                        Text(text = "Matrícula: $matricula")
                        Text(text = "Asignatura: $asignatura")
                        Text(text = "Hora: $hora")
                        Text(text = "Fecha: $fecha")
                    }
                }
            }
        }
    }
